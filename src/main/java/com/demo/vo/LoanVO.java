package com.demo.vo;

import com.demo.entity.Loan;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanVO {

    private Long loanId;

    private String customerId;

    private String lenderId;

    private double amount;

    private double remainingAmount;

    private Date paymentDate;

    private double interestPerDay;

    private Date dueDate;

    private double penaltyDay;

    private String cancel;

    public LoanVO(Loan loan){
        this.loanId = loan.getLoanId();
        this.customerId = loan.getCustomerId();
        this.amount = loan.getAmount();
        this.dueDate = loan.getDueDate();
        this.lenderId = loan.getLenderId();
        this.interestPerDay = loan.getInterestPerDay();
        this.paymentDate = loan.getPaymentDate();
        this.remainingAmount = loan.getRemainingAmount();
        this.penaltyDay = loan.getPenaltyDay();
        this.cancel = loan.getCancel();
    }
}
