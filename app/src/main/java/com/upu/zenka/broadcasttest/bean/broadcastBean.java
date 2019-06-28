package com.upu.zenka.broadcasttest.bean;

import java.util.List;

/**
 * Created by ThinkPad on 2018/11/13.
 */

public class broadcastBean {
    public List<Data> getDatas() {
        return data;
    }

    public void setDatas(List<Data> datas) {
        this.data = datas;
    }

    private List<Data> data;
    private int code;
    private String message;
    private String serverTime;



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }


    public String toString(){
        return "data="+data
                +";"+"code="+code
                +";"+"message="+message
                +";"+"serverTime="+serverTime;
    }


    public class Data{
        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        private int type;
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        private int times;

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        private String suffix;
    }
}
