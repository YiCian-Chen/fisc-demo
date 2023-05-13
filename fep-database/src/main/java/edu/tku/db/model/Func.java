package edu.tku.db.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "SYS_FUNC")
@Entity
public class Func {
    @Id
    @Column(name = "func_id")
    private String funcId;
    @Column(name = "func_name")
    private String funcName;
    @Column(name = "func_url")
    private String funcUrl;
    @Column(name = "parent_id")
    private String parentId;
    @Column(name = "order_no")
    private long orderNo;
    @Column(name = "permission")
    private String permission;

}
