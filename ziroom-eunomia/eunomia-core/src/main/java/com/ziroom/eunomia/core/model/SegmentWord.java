package com.ziroom.eunomia.core.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class SegmentWord implements Serializable{

    private static final long serialVersionUID = -2062000673772905737L;

    private String word;

    private Integer count;

    // 统计数量
    public void increase() {
        if (this.count == null) {
            this.count = 1;
        }
        this.count ++;
    }
}
