package edu.tku.db.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "SYS_ROLE")
@Entity
public class Role {
    @Id
    @Column(name = "role_id")
    private String roleId;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "role_desc")
    private String roleDesc;
    @Column(name = "functions")
    private String functions;

    @Transient
    private String action;
}
