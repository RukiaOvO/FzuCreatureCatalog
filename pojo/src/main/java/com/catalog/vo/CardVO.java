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
    private List<Img> imgs;
    private String intro;
    private int is_like;
    private int is_star;
    private int like_num;
    private String location;
    private String pet_name;
}
