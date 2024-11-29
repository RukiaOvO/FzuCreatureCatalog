package com.catalog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card
{
    private int id;
    private int imgId;
    private String animalName;
    private String species;
    private String personality;
    private String introduction;
    private String locationDescription;
    private double longitude;
    private double latitude;
    private int followNum;
    private int dailyLikeNum;
    private int totalLikeNum;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private int status;
}
