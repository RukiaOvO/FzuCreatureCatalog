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
    private int card_id;
    private String img_url;
    private String pet_name;
    private String location;
    private String intro;
    private int like_num;
    private int follow_num;
    private boolean like;
    private boolean follow;
    private List<Img> imgs;
    private double distance;
}
