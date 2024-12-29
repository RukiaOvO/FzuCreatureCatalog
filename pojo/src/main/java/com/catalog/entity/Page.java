package com.catalog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page
{
    private int id;
    private String nickname;
    private String description;
    private String position;
    private String imgUrl;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
