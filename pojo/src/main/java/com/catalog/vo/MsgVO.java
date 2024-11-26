package com.catalog.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsgVO implements Serializable
{
    private int msg_id;
    private int is_read;
    private String content;
    private String upload_time;
}
