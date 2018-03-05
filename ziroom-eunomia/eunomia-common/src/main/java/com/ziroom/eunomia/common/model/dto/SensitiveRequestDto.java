package com.ziroom.eunomia.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveRequestDto implements Serializable {

    private static final long serialVersionUID = -4091112223658684044L;

    // 待校验文本内容
    private String content;

    // 组织权限校验码
    private String token;

    // 组织机构Id
    private String orgFid;

    @Override
    public String toString() {
        return "{" +
                "content='" + content + '\'' +
                '}';
    }
}
