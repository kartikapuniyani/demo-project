package com.demo.controller;

import com.demo.co.AggregateCO;
import com.demo.co.LoanCO;
import com.demo.service.LoanService;
import com.demo.vo.LoanVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Long> save( @RequestBody LoanCO loanCO) {
        Long userId = loanService.save(loanCO);
        return ResponseEntity.ok().body(userId);
    }

    @GetMapping(path = "/{loanId}")
    public ResponseEntity<LoanVO> getById(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.getByLoanId(loanId));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<LoanVO>> getAll() {
        return ResponseEntity.ok(loanService.getAll());
    }

    @GetMapping(path = "/customer/{customerId}")
    public ResponseEntity<List<LoanVO>> getByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(loanService.getByCustomerId(customerId));
    }

    @GetMapping(path = "/lender/{lenderId}")
    public ResponseEntity<List<LoanVO>> getByLenderId(@PathVariable String lenderId) {
        return ResponseEntity.ok(loanService.getByLenderId(lenderId));
    }

    @GetMapping(path = "/aggregate/lender")
    public ResponseEntity<Map<String, AggregateCO>> getAggregateByLender() {
        return ResponseEntity.ok(loanService.getAggregateByLender());
    }

    @GetMapping(path = "/aggregate/customer")
    public ResponseEntity<Map<String, AggregateCO>> getAggregateByCustomer() {
        return ResponseEntity.ok(loanService.getAggregateByCustomer());
    }

    @GetMapping(path = "/aggregate/interest")
    public ResponseEntity<Map<Double, AggregateCO>> getAggregateByInterest() {
        return ResponseEntity.ok(loanService.getAggregateByInterest());
    }
}