package com.catalog.vo;

import com.catalog.entity.Img;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardVO implements Serializable
{
    private long cardid;
    private String imgurl;
    private List<Img> imgs;
    private String intro;
    private long isLike;
    private long isStar;
    private String likeNum;
    private String location;
    private String petName;
}
