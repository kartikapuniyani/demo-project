package com.demo.repository;

import com.demo.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long>{

    List<Loan> findByCustomerId(String customerId);

    List<Loan> findByLenderId(String lenderId);
}