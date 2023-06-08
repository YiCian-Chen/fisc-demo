package edu.tku.db.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "SYS_USER")
@Entity
public class User {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "role_id")
    private String roleId;
    @Column(name = "branch_id")
    private String branchId;
    @Column(name = "dep_id")
    private String depId;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "token")
    private String token;
    @Column(name = "last_login_ip")
    private String lastLoginIp;
    @Column(name = "last_login_time")
    private Date lastLoginTime;
    @Column(name = "enabled")
    private boolean enabled;

    @Transient
    private String action;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", insertable = false, updatable = false)
    private Role role;
}
