package com.demo.service.serviceImpl;

import com.demo.co.AggregateCO;
import com.demo.co.LoanCO;
import com.demo.repository.LoanRepository;
import com.demo.entity.Loan;
import com.demo.service.LoanService;
import com.demo.vo.LoanVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LoanServiceImpl implements LoanService {

    private LoanRepository loanRepository;

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public Long save(LoanCO loanCO) {
        Loan loan = new Loan();
        if (loanCO.getPaymentDate().after(loanCO.getDueDate())) {
            log.error("Loan {} has crossed the due date.", loan.getLoanId());
            throw new IllegalArgumentException("Payment date cannot be greater than the due date.");
        }
        loan.setCustomerId(loanCO.getCustomerId());
        loan.setAmount(loanCO.getAmount());
        loan.setDueDate(loanCO.getDueDate());
        loan.setLenderId(loanCO.getLenderId());
        loan.setInterestPerDay(loanCO.getItrPerDay());
        loan.setPaymentDate(loanCO.getPaymentDate());
        loan.setRemainingAmount(loanCO.getRemAmount());
        loan.setPenaltyDay(loanCO.getPenaltyDay());
        loan.setCancel(loanCO.getCancel());
        loan = loanRepository.save(loan);
        return loan.getLoanId();
    }

    @Override
    public LoanVO getByLoanId(Long loanId) {
        Loan loan = findById(loanId);
        return new LoanVO(loan);
    }

    @Override
    public List<LoanVO> getAll() {
        return loanRepository.findAll().stream().map(LoanVO::new).collect(Collectors.toList());
    }

    @Override
    public List<LoanVO> getByLenderId(String lenderId) {
        return loanRepository.findByLenderId(lenderId).stream().map(LoanVO::new).collect(Collectors.toList());
    }

    @Override
    public List<LoanVO> getByCustomerId(String customerId) {
        return loanRepository.findByCustomerId(customerId).stream().map(LoanVO::new).collect(Collectors.toList());
    }

    @Override
    public Map<Double, AggregateCO> getAggregateByInterest() {
        return loanRepository.findAll().stream()
                .collect(Collectors.groupingBy(Loan::getInterestPerDay,
                        Collectors.collectingAndThen(
                                Collectors.summarizingDouble(Loan::getRemainingAmount),
                                summary -> new AggregateCO(summary.getSum(), summary.getAverage(), summary.getMax())
                        )
                ));
    }

    @Override
    public Map<String, AggregateCO> getAggregateByCustomer() {
        return loanRepository.findAll().stream()
                .collect(Collectors.groupingBy(Loan::getCustomerId,
                        Collectors.collectingAndThen(
                                Collectors.summarizingDouble(Loan::getRemainingAmount),
                                summary -> new AggregateCO(summary.getSum(), summary.getAverage(), summary.getMax())
                        )
                ));
    }

    @Override
    public Map<String, AggregateCO> getAggregateByLender() {
        return loanRepository.findAll().stream()
                .collect(Collectors.groupingBy(Loan::getLenderId,
                        Collectors.collectingAndThen(
                                Collectors.summarizingDouble(Loan::getRemainingAmount),
                                summary -> new AggregateCO(summary.getSum(), summary.getAverage(), summary.getMax())
                        )
                ));
    }

    private Loan findById(Long id) {
        return loanRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Loan not found for the given id"));
    }
}