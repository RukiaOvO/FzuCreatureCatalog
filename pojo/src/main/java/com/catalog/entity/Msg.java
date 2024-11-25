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
public class Msg implements Serializable
{
    private int msg_id;
    private String content;
    private LocalDateTime create_time;
    private int status;
}
