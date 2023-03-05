package com.akshay.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> implements Serializable {
    private static final long serialVersionUID = 1234567654321L;
    private Boolean ok;
    private String code;
    private T result;
    private ErrorResponse error;

    public Response(T result, String code) {
        this.ok = true;
        this.code = code;
        this.result = result;
        this.error = null;
    }

    public static <T> ResponseBuilder<T> builder() {
        return new ResponseBuilder<>();
    }

    public Boolean getOk() {
        return this.ok;
    }

    public String getCode() {
        return this.code;
    }

    public T getResult() {
        return this.result;
    }

    public ErrorResponse getError() {
        return this.error;
    }

    public void setOk(final Boolean ok) {
        this.ok = ok;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setResult(final T result) {
        this.result = result;
    }

    public void setError(final ErrorResponse error) {
        this.error = error;
    }


    public String toString() {
        Boolean ok = this.getOk();
        return "BaseRestResponse(ok=" + ok + ", code=" + this.getCode() + ", result=" + this.getResult() + ", error=" + this.getError() + ")";
    }

    public Response(final Boolean ok, final String code, final T result, final ErrorResponse error) {
        this.ok = ok;
        this.code = code;
        this.result = result;
        this.error = error;
    }

    public Response() {
    }

    public static class ResponseBuilder<T> {
        private Boolean ok;
        private String code;
        private T result;
        private ErrorResponse error;

        ResponseBuilder() {
        }

        public ResponseBuilder<T> ok(final Boolean ok) {
            this.ok = ok;
            return this;
        }

        public ResponseBuilder<T> code(final String code) {
            this.code = code;
            return this;
        }

        public ResponseBuilder<T> result(final T result) {
            this.result = result;
            return this;
        }

        public ResponseBuilder<T> error(final ErrorResponse error) {
            this.error = error;
            return this;
        }

        public Response<T> build() {
            return new Response<>(this.ok, this.code, this.result, this.error);
        }

        public String toString() {
            return "Response.ResponseBuilder(ok=" + this.ok + ", code=" + this.code + ", result=" + this.result + ", error=" + this.error + ")";
        }
    }
}
