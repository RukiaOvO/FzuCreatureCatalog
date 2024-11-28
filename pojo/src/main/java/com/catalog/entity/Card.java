package com.catalog.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class Card
{
    private int id;
    private int imgId;
    private String animalName;
    private String species;
    private String personality;
    private String introduction;
    private String locationDescription;
    private int followNum;
    private int dailyLikeNum;
    private int totalLikeNum;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private int status;
}
