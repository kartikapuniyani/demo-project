package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String lenderId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private double remainingAmount;

    @Column(nullable = false)
    private Date paymentDate;

    @Column(nullable = false)
    private double interestPerDay;

    @Column(nullable = false)
    private Date dueDate;

    @Column(nullable = false)
    private double penaltyDay;

    private String cancel;
}