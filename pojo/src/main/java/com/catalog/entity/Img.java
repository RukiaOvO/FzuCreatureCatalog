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
public class Img
{
    private int id;
    private String url;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String imgKey;
}
