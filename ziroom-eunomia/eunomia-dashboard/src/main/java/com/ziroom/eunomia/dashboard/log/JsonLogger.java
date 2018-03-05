package com.ziroom.eunomia.dashboard.log;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonLogger {
    static Logger logger = LoggerFactory.getLogger("json_logger");

    public static <T> void log(T object) {
        log(logger, object);
    }

    public static void log( String... values) {
        log(logger, new Object(), values);
    }

    public static <T> void log(T object, String... values) {
        log(logger, object, values);
    }

    protected static <T> void log(Logger logger, T object, String... values) {
        if (object == null) {
            return;
        }
        Object o = JSONObject.toJSON(object);
        JSONObject json;
        if (o instanceof JSONObject) {
            json = (JSONObject) o;
        } else {
            json = new JSONObject();
            json.put("_o", o);
        }

        json.put("_ts", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));

        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                json.put("_v" + i, values[i]);
            }
        }

        logger.info(json.toJSONString());
    }
}
