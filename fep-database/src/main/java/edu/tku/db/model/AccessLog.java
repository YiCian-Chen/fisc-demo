package edu.tku.db.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ACCESS_LOG")
@Data
public class AccessLog {
    @Id
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "action")
    private String action;
    @Column(name = "request_time")
    private Date requestTime;
    @Column(name = "request_url")
    private String requestUrl;
    @Column(name = "request_method")
    private String requestMethod;
    @Column(name = "response_code", length = 5)
    private String responseCode;
    @Column(name = "response_message", length = 99999)
    private String responseMessage;
}
