package com.catalog.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageThumbnailVO
{
    private int id;
    private String nickname;
    private String description;
    private String position;
    private String imgUrl;
}
