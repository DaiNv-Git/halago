package com.hitex.halago.model.logTransaction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//@MappedSuperclass
@Entity
@Table(name = "log_transaction")
public class LogTransactionBase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "session_id")
    private String sessionId;
    @Column(name = "ip")
    private String ip;
    @Column(name = "request")
    private String request;
    @Column(name = "response")
    private String response;
    @Column(name = "uri")
    private String uri;
    @Column(name = "ws_code")
    private String wsCode;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    private Date startTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "time_run")
    private Long timeRun;
    @Column(name = "error_code")
    private Integer errorCode;
    @Column(name = "error_message")
    private String message;
//    @Column(name = "username")
//    private String username;
//    @Column(name = "language")
//    private String language;
//    @Column(name = "app_name")
//    private String appName;
//    @Column(name = "version_app_name")
//    private String versionAppName;
//    @Column(name = "version_code")
//    private Integer versionCode;
//    @Column(name = "device_name")
//    private String deviceName;
//    @Column(name = "device_os")
//    private String deviceOs;
//    @Column(name = "version_os")
//    private String versionOs;
//    @Column(name = "imei")
//    private String imei;
    @Column(name = "host_name")
    private String hostName;
    @Column(name = "status")
    private Long status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getWsCode() {
        return wsCode;
    }

    public void setWsCode(String wsCode) {
        this.wsCode = wsCode;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getTimeRun() {
        return timeRun;
    }

    public void setTimeRun(Long timeRun) {
        this.timeRun = timeRun;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

}
