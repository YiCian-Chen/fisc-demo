package edu.tku.db.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "FISC_BANK")
@Entity
public class Bank {
    @Id
    @Column(name = "BANK_id", length = 7)
    private String bankId;

    @Column(name = "BANK_name", length = 50)
    private String bankName;

    @Column(name = "BANK_address", length = 150)
    private String bankAddress;

    @Column(name = "BANK_tel", length = 15)
    private String bankTel;

    @Column(name = "enabled")
    private boolean enabled;

    @Transient
    private String action;
}
