package com.catalog.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Card
{
    private int id;
    private int img_id;
    private String animal_name;
    private String species;
    private String personality;
    private String introduction;
    private String locationDescription;
    private int follow_num;
    private int daily_like_num;
    private int total_like_num;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
    private int status;
}
