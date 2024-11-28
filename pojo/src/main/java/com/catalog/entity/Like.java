package com.catalog.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Like
{
    private int id;
    private int user_id;
    private int card_id;
    private LocalDateTime create_time;
}
