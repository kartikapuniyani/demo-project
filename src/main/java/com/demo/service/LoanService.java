package com.demo.service;

import com.demo.co.AggregateCO;
import com.demo.co.LoanCO;
import com.demo.vo.LoanVO;

import java.util.List;
import java.util.Map;

public interface LoanService {

    Long save(LoanCO loanCO);

    LoanVO getByLoanId(Long loanId);

    List<LoanVO> getAll();

    List<LoanVO> getByLenderId(String lenderId);

    List<LoanVO> getByCustomerId(String customerId);

    Map<Double, AggregateCO> getAggregateByInterest();

    Map<String, AggregateCO> getAggregateByCustomer();

    Map<String, AggregateCO> getAggregateByLender();
}
