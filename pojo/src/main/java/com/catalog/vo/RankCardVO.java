package com.catalog.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankCardVO implements Serializable
{
    private int card_id;
    private String img_url;
    private String pet_name;
    private String location;
    private String intro;
    private int like_num;
    private int follow_num;
    private boolean is_like;
    private boolean is_follow;
}
