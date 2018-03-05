package com.ziroom.eunomia.dashboard.model.dto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;

/**
 * 统一结果统计响应
 * @author jixd
 * @created 2017年08月16日 11:31:17
 * @param
 * @return
 */
public class Result implements Serializable {

    private static final long serialVersionUID = -494849412079027242L;

    public static final int SUCCESS = 0;

    public static final int ERROR = 1;

    private int status;
    private String message;
    private Object data;

    public Result() {
        status = SUCCESS;
    }

    public int getStatus() {
        return status;
    }

    public Result setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }



    @Override
    public String toString() {
        return JSONObject.toJSONString(this, SerializerFeature.WriteMapNullValue);
    }

}
