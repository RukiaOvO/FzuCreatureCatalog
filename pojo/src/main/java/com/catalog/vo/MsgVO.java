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
    private String content;
    private long is_read;
    private long msg_id;
    private String upload_time;
}
