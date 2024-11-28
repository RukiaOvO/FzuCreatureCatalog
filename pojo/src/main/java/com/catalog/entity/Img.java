package com.catalog.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Img
{
    private int id;
    private String url;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String imgKey;
}
