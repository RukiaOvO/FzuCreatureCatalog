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

    private int img_id;

    private String open_id;

    private String nickname;;

    private LocalDateTime create_time;

    private LocalDateTime update_time;
}
