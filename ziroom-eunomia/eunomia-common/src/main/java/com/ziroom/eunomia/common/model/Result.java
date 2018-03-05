package com.ziroom.eunomia.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>todo : 可自定义状态的返回结果集</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2017年11月09日 14:27
 * @Version 1.0
 * @Since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {

    private static final long serialVersionUID = -1214787311053045595L;

    private StatusType status;

    private Object object;

    public static ResultBuilder builder() {
        return new ResultBuilder();
    }

    public static ResultBuilder ok() {
        return status(Status.SUCCESS);
    }

    public static ResultBuilder ok(Object data) {
        ResultBuilder b = ok();
        b.data(data);
        return b;
    }

    public static ResultBuilder status(Status status) {
        return ResultBuilder.newInstance().status(status);
    }

    public static ResultBuilder status(String statusCode) {
        return ResultBuilder.newInstance().status(statusCode);
    }

    public static ResultBuilder status(String statusCode, String message) {
        return ResultBuilder.newInstance().status(statusCode, message);
    }

    public static class ResultBuilder {
        private StatusType status;
        private Object data;

        ResultBuilder() {
        }

        public ResultBuilder status(StatusType status) {
            this.status = status;
            return this;
        }

        public ResultBuilder data(Object data) {
            this.data = data;
            return this;
        }

        public Result build() {
            return new Result(this.status, this.data);
        }

        public String toString() {
            return "Result.ResultBuilder(status=" + this.status + ", data=" + this.data + ")";
        }

        public static ResultBuilder newInstance() {
            return new ResultBuilder();
        }

        public ResultBuilder status(String status) {
            return status(status, "");
        }

        public ResultBuilder status(String status, String message) {
            StatusType st = Status.fromStatusCode(status);
            if (st == null) {
                st = new StatusCustom(status, message);
            }
            return status(st);
        }
    }

    @Getter
    public enum Status implements StatusType {

        SUCCESS("000000", "成功"),
        ERROR("000001", "失败"),
        UNKNOWN_ERROR("000002", "系统未知错误"),
        AUTHENTICATION_FAIL("000003", "鉴权失败"),
        MISS_PARAMETER("000004", "缺失参数"),
        ILLEGAL_PARAMETER("000005", "非法参数"),
        USERNAME_OR_PASSWORD_ERROR("000006", "用户名或密码错误");

        private final String code;

        private final String msg;

        Status(final String code, final String message) {
            this.code = code;
            this.msg = message;
        }

        public static Status fromStatusCode(final String statusCode) {
            for (Status s : Status.values()) {
                if (s.code.equals(statusCode)) {
                    return s;
                }
            }
            return null;
        }

        @Override
        public String getStatusCode() {
            return code;
        }

        @Override
        public String toString() {
            return msg;
        }
    }

    public interface StatusType {
        String getStatusCode();
    }

    @Data
    @AllArgsConstructor
    public static class StatusCustom implements StatusType {

        private String code;

        private String message;

        @Override
        public String getStatusCode() {
            return code;
        }
    }
}
