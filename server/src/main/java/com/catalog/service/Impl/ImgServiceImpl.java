package com.catalog.service.Impl;

import com.catalog.constant.ImgConstant;
import com.catalog.context.BaseContext;
import com.catalog.entity.Img;
import com.catalog.mapper.ImgMapper;
import com.catalog.mapper.UserMapper;
import com.catalog.properties.ImgBedProperties;
import com.catalog.service.ImgService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ImgServiceImpl implements ImgService
{
    @Autowired
    private ImgBedProperties imgBedProperties;
    @Autowired
    private ImgMapper imgMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public String getImgBedToken()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("email", imgBedProperties.getEmail());
        paramMap.put("password", imgBedProperties.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(paramMap, headers);
        String result = new RestTemplate().postForObject(imgBedProperties.getUrl() + ImgConstant.TOKEN_API, entity, String.class);

        try
        {
            JsonNode jsonNode = objectMapper.readTree(result);
            if(jsonNode.get("data").get("token") != null)
            {
                result = jsonNode.get("data").get("token").asText();
                log.info("获取图床Token:{}", result);
                return result;
            }
            throw new RuntimeException(ImgConstant.GET_TOKEN_ERROR);
        }
        catch (Exception e)
        {
            log.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public Img uploadImgToBed(File file)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(getImgBedToken());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        String result = new RestTemplate().postForObject(imgBedProperties.getUrl() + ImgConstant.UPLOAD_IMG_API, entity, String.class);
        Img newImg = new Img();
        String imgUrl;
        String imgKey;
        try
        {
            JsonNode jsonNode = new ObjectMapper().readTree(result);
            if(jsonNode.get("status").asText().equals("false"))
            {
                throw new IllegalStateException(ImgConstant.UPLOAD_REQUEST_ERROR);
            }
            else
            {
                imgUrl = jsonNode.get("data").get("links").get("url").asText().replace("http", "https");
                imgKey = jsonNode.get("data").get("key").asText();
                newImg.setUrl(imgUrl);
                newImg.setImg_key(imgKey);
                log.info("获取图片Url:{} Key:{}", imgUrl, imgKey);
                newImg.setId(imgMapper.insert(newImg));
                int userId = BaseContext.getCurrentId();
                userMapper.addUserImg(userId, newImg.getId());
                return newImg;
            }
        }
        catch (IOException e)
        {
            log.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteImgInBed(String key)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getImgBedToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> result = new RestTemplate().exchange(imgBedProperties.getUrl() + ImgConstant.DELETE_IMG_API + key, HttpMethod.DELETE, entity, String.class);
        if(!(result.getStatusCode() == HttpStatus.OK))
        {
            throw new IllegalStateException(ImgConstant.DELETE_IMG_ERROR);
        }
        log.info("删除图片:{} 成功", key);
    }

    @Override
    public Img getImageById(int imgId)
    {
        return imgMapper.getImageById(imgId);
    }
}
