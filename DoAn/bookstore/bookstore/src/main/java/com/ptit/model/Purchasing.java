package com.ptit.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name="purchasing")
@Data
public class Purchasing {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_purchasing")
    private long id_purchasing;


    @Column(name="id_supplier")
    private int id_supplier;

    @Column(name="status")
    private int status;

    @Column(name="supplier_name")
    private  String supplier_name;

    @Column(name="phonenumber")
    private  String phonenumber;

    @Column(name="email")
    private  String email;

    @Column(name="totalprice")
    private  BigDecimal totalprice;





}


























