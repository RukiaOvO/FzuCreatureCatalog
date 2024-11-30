package com.catalog.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.catalog.context.BaseContext;
import com.catalog.entity.Card;
import com.catalog.entity.Img;
import com.catalog.entity.Msg;
import com.catalog.exception.BaseException;
import com.catalog.exception.LoginFailedException;
import com.catalog.mapper.*;
import com.catalog.properties.WeChatProperties;
import com.catalog.service.UserService;
import com.catalog.properties.JwtProperties;
import com.catalog.entity.User;
import com.catalog.dto.UserLoginDTO;
import com.catalog.constant.MessageConstant;
import com.catalog.utils.HttpClientUtil;
import com.catalog.utils.JwtUtil;
import com.catalog.constant.JwtClaimsConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MsgMapper msgMapper;
    @Autowired
    private FollowMapper followMapper;
    @Autowired
    private UploadMapper uploadMapper;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private ImgMapper imgMapper;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private CardMapper cardMapper;

    @Override
    public User wxLogin(UserLoginDTO userLoginDTO)
    {
        String openid = getOpenid(userLoginDTO.getCode());
        if(openid == null || openid.isEmpty())
        {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        User user = userMapper.getUserByOpenId(openid);
        if(user == null)
        {
            user = User.builder()
                    .openId(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        return user;
    }

    private String getOpenid(String code)
    {
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");

        String json = HttpClientUtil.doGet(weChatProperties.getLoginUrl(), map, null);
        JSONObject jsonObject = JSON.parseObject(json);
        return jsonObject.getString("openid");
    }

    @Override
    public String generateToken(User user)
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        return JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
    }

    @Override
    public User getUserById(int id)
    {
        return userMapper.getUserById(id);
    }

    @Override
    public int getUserFollowNumById(int id)
    {
        return followMapper.getFollowNumByUserId(id);
    }

    @Override
    public int getUserLikeNumById(int id)
    {
        return likeMapper.getLikeNumByUserId(id);
    }

    @Override
    public int getUserImgNumById(int id)
    {
        return imgMapper.getImgNumByUserId(id);
    }

    @Transactional
    @Override
    public List<Card> getFollowCardsById(int userId, int sortRule)
    {
        List<Integer> cardIds;
        if(sortRule == 0)
        {
            cardIds = followMapper.getUploadSortedCardIdsByUserId(userId);
        }
        else
        {
            cardIds = followMapper.getFollowSortedCardIdsByUserId(userId);
        }
        if(cardIds == null || cardIds.isEmpty()) return null;
        return cardMapper.getAcceptedCardsByIds(cardIds);
    }

    @Transactional
    @Override
    public List<Card> getUploadCardsById(int id)
    {
        List<Integer> cardIds = uploadMapper.getCardIdsByUserId(id);
        if(cardIds == null || cardIds.isEmpty()) return null;
        return cardMapper.getAcceptedCardsByIds(cardIds);
    }

    @Transactional
    @Override
    public List<Msg> getUserMsgById(int id)
    {
        List<Integer> msgIds = userMapper.getMsgIdsById(id);
        if(msgIds == null || msgIds.isEmpty()) return null;
        return msgMapper.getMsgByIds(msgIds);
    }

    @Transactional
    @Override
    public void deleteUserMsgByMsgId(int id)
    {
        userMapper.deleteMsgByMsgId(id);
        msgMapper.deleteMsgById(id);
    }

    @Override
    public List<Integer> getMsgIdsById(int id)
    {
        return userMapper.getMsgIdsById(id);
    }

    @Override
    public Integer getMsgIdById(int id)
    {
        return userMapper.getMsgIdById(id);
    }

    @Transactional
    @Override
    public void likeCardById(int userId, int cardId)
    {
          List<Integer> cardIds = userMapper.getLikeCardIdsById(userId);
          if(cardIds == null || cardIds.isEmpty()) return;
          if(cardIds.contains(cardId))
          {
              userMapper.dislikeCard(userId, cardId);
              cardMapper.disLike(cardId);
          }
          else
          {
              userMapper.likeCard(userId, cardId);
              cardMapper.like(cardId);
          }
    }

    @Transactional
    @Override
    public void followCardById(int userId, int cardId)
    {
        List<Integer> cardIds = userMapper.getFollowCardIdsById(userId);
        if(cardIds == null || cardIds.isEmpty()) return;
        if(cardIds.contains(cardId))
        {
            userMapper.unfollowCard(userId, cardId);
            cardMapper.unFollow(cardId);
        }
        else
        {
            userMapper.followCard(userId, cardId);
            cardMapper.follow(cardId);
        }
    }

    @Override
    public void updateUserInfo(String nickname, Img img)
    {
        int imgId = img.getId();
        int userId = BaseContext.getCurrentId();
        userMapper.updateUserInfo(userId, nickname, imgId);
    }

    @Override
    public void uploadCard(int userId, int cardId)
    {
        uploadMapper.userUploadCard(userId, cardId);
    }

    @Override
    public boolean isFollowCard(Card c)
    {
        return followMapper.getFollowCard(BaseContext.getCurrentId(), c.getId()) != null;
    }

    @Override
    public boolean isLikeCard(Card c)
    {
        return likeMapper.getLikeCard(BaseContext.getCurrentId(), c.getId()) != null;
    }
}
