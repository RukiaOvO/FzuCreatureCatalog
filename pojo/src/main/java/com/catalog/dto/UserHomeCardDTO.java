package com.catalog.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserHomeCardDTO implements Serializable
{
    private int num;
    private int offset;
    private double longitude;
    private double latitude;
}
