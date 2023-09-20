package com.demo.co;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanCO {

    private String customerId;

    private String lenderId;

    private double amount;

    private double remAmount;

    private Date paymentDate;

    private double itrPerDay;

    private Date dueDate;

    private double penaltyDay;

    private String cancel;

}