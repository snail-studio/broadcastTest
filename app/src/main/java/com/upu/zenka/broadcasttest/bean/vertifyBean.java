package com.upu.zenka.broadcasttest.bean;

/**
 * Created by ThinkPad on 2018/11/18.
 */

public class vertifyBean {
    private Data data;
    private int code;
    private String message;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

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
        public Configuration getConfiguration() {
            return configuration;
        }

        public void setConfiguration(Configuration configuration) {
            this.configuration = configuration;
        }

        private Configuration configuration;
        private String schoolname;

        public String getSchoolname() {
            return schoolname;
        }

        public void setSchoolname(String schoolname) {
            this.schoolname = schoolname;
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        private String classname;
        private String schoolid;

        public String getSchoolid() {
            return schoolid;
        }

        public void setSchoolid(String schoolid) {
            this.schoolid = schoolid;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        private String classid;

        public ApResBean getApinfo() {
            return apinfo;
        }

        public void setApinfo(ApResBean apinfo) {
            this.apinfo = apinfo;
        }

        private ApResBean apinfo;

    }
    public class Configuration{
        public int getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        private int volume;
        private String callFormate;
        private int callTimes;

        public String getCallFormate() {
            return callFormate;
        }

        public void setCallFormate(String callFormate) {
            this.callFormate = callFormate;
        }

        public int getCallTimes() {
            return callTimes;
        }

        public void setCallTimes(int callTimes) {
            this.callTimes = callTimes;
        }

        public int getNoticeTimes() {
            return noticeTimes;
        }

        public void setNoticeTimes(int noticeTimes) {
            this.noticeTimes = noticeTimes;
        }

        private int noticeTimes;

        public int getWorkpattern() {
            return workpattern;
        }

        public void setWorkpattern(int workpattern) {
            this.workpattern = workpattern;
        }

        private int workpattern;


        public WorkTime getWorktime() {
            return worktime;
        }

        public void setWorktime(WorkTime worktime) {
            this.worktime = worktime;
        }

        private WorkTime worktime;
    }
    public class WorkTime{
        private String amtimein;
        private String amtimeleave;
        private String pmtimein;

        public String getAmtimein() {
            return amtimein;
        }

        public void setAmtimein(String amtimein) {
            this.amtimein = amtimein;
        }

        public String getAmtimeleave() {
            return amtimeleave;
        }

        public void setAmtimeleave(String amtimeleave) {
            this.amtimeleave = amtimeleave;
        }

        public String getPmtimein() {
            return pmtimein;
        }

        public void setPmtimein(String pmtimein) {
            this.pmtimein = pmtimein;
        }

        public String getPmtimeleave() {
            return pmtimeleave;
        }

        public void setPmtimeleave(String pmtimeleave) {
            this.pmtimeleave = pmtimeleave;
        }

        private String pmtimeleave;
    }
}
