package com.catalog.service.Impl;

import com.catalog.constant.ImgConstant;
import com.catalog.entity.Img;
import com.catalog.properties.ImgBedProperties;
import com.catalog.service.ImgService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ImgServiceImpl implements ImgService
{
    @Autowired
    private ImgBedProperties imgBedProperties;

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
                return jsonNode.get("data").get("token").asText();
            }
            throw new RuntimeException(ImgConstant.GET_TOKEN_ERROR);
        }
        catch (Exception e)
        {
            log.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String uploadImgToBed(File file)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(getImgBedToken());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        String result = new RestTemplate().postForObject(imgBedProperties.getUrl() + ImgConstant.UPLOAD_IMG_API, entity, String.class);
        try
        {
            JsonNode jsonNode = new ObjectMapper().readTree(result);
            if(jsonNode.get("status").asText().equals("false"))
            {
                throw new IllegalStateException(ImgConstant.UPLOAD_REQUEST_ERROR);
            }
            else
            {
                return jsonNode.get("data").get("links").get("url").asText();
            }
        }
        catch (IOException e)
        {
            log.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteImgInBed(String key) {
        return "";
    }
}
