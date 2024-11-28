package com.catalog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable
{
    private int id;

    private String openId;

    private String nickname;;

    private int imgId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
