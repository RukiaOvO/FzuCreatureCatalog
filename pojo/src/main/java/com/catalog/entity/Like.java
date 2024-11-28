package com.catalog.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Like
{
    private int id;
    private int userId;
    private int cardId;
    private LocalDateTime createTime;
}
