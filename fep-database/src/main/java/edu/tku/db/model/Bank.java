package edu.tku.db.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "FISC_BANK")
@Entity
public class Bank {
    @Id
    @Column(name = "BANKCODE", length = 3)
    private String bankCode;

    @Column(name = "BANKNAME", length = 36)
    private String bankName;

    @Column(name = "TELZONE", length = 3)
    private String telZone;

    @Column(name = "TELNO", length = 10)
    private String telNo;

    @Column(name = "UPDATEDATE", length = 8)
    private String updatedate;

    @Transient
    private String action;
}
