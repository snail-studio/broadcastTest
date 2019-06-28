package com.upu.zenka.broadcasttest.bean;

import java.util.List;

/**
 * Created by ThinkPad on 2018/11/19.
 */

public class nettestBean {
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    private Data data;
    private int code;
    private String message;



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

    private String serverTime;

    public class Data{
        private String deviceid;

        public String getDeviceid() {
            return deviceid;
        }

        public void setDeviceid(String deviceid) {
            this.deviceid = deviceid;
        }

        public List<Changed> getChanged() {
            return changed;
        }

        public void setChanged(List<Changed> changed) {
            this.changed = changed;
        }

        private List<Changed> changed;
    }
    public class Changed{
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        private String value;
        private String param;
    }
}
