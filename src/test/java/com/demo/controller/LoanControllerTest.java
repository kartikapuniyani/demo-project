package com.demo.controller;

import com.demo.co.AggregateCO;
import com.demo.co.LoanCO;
import com.demo.service.LoanService;
import com.demo.vo.LoanVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = LoanController.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration
@EnableWebMvc
public class LoanControllerTest {


    @MockBean
    private LoanService loanService;

    @Autowired
    protected WebApplicationContext context;

    protected MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                                .build();
    }

    @Test
    public void createTest() throws Exception {
        LoanCO loanCO = LoanCO.builder().amount(10d).remAmount(20d).cancel(null).customerId("C1").itrPerDay(10d).
                lenderId("len1").penaltyDay(1d).paymentDate(new Date()).dueDate(new Date()).build();
        when(loanService.save(any(LoanCO.class))).thenReturn(Long.valueOf(4));

        mockMvc
                .perform(post("/loans/add")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(loanCO)))
                .andExpect(status().isOk())
        .andExpect(content().string(String.valueOf(4)));
    }

    @Test
    public void getLoanByLoanId() throws Exception{
        LoanVO loanVO = LoanVO.builder().loanId(1L).amount(10d).remainingAmount(20d).cancel(null).customerId("C1").interestPerDay(10d).
                lenderId("len1").penaltyDay(1d).paymentDate(new Date()).dueDate(new Date()).build();

        when(loanService.getByLoanId(1L)).thenReturn(loanVO);
        mockMvc
                .perform(MockMvcRequestBuilders.get("/loans/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(loanVO)));
    }

    @Test
    public void getLoanByCustomerId() throws Exception{
        List<LoanVO> loanVOS = Arrays.asList(
                new LoanVO(2L, "c1", "len1", 1000, 500, new Date(1695363063L), 0.1,  new Date(1696054263L), 500, null),
                new LoanVO(2L, "c2", "len2", 1000, 500, new Date(1695363063L), 0.1,  new Date(1696054263L), 500, null),
                new LoanVO(2L, "c1", "len1", 1000, 500, new Date(1695363063L), 0.1,  new Date(1696054263L), 500, null)
        );

        when(loanService.getByCustomerId("c1")).thenReturn(loanVOS);
        mockMvc
                .perform(MockMvcRequestBuilders.get("/loans/customer/c1"))
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(loanVOS)));
    }

    @Test
    public void getLoanByLenderId() throws Exception{
        List<LoanVO> loanVOS = Arrays.asList(
                new LoanVO(2L, "c1", "len1", 1000, 500, new Date(1695363063L), 0.1,  new Date(1696054263L), 500, null),
                new LoanVO(2L, "c1", "len1", 1000, 500, new Date(1695363063L), 0.1,  new Date(1696054263L), 500, null),
                new LoanVO(2L, "c1", "len1", 1000, 500, new Date(1695363063L), 0.1,  new Date(1696054263L), 500, null)
        );

        when(loanService.getByLenderId("len1")).thenReturn(loanVOS);
        mockMvc
                .perform(MockMvcRequestBuilders.get("/loans/lender/len1"))
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(loanVOS)));
    }

    @Test
    public void getAllLoan() throws Exception{
        List<LoanVO> loanVOS = Arrays.asList(
                new LoanVO(2L, "c1", "len1", 1000, 500, new Date(1695363063L), 0.1,  new Date(1696054263L), 500, null),
                new LoanVO(2L, "c1", "len1", 1000, 500, new Date(1695363063L), 0.1,  new Date(1696054263L), 500, null),
                new LoanVO(2L, "c1", "len1", 1000, 500, new Date(1695363063L), 0.1,  new Date(1696054263L), 500, null)
        );

        when(loanService.getAll()).thenReturn(loanVOS);
        mockMvc
                .perform(MockMvcRequestBuilders.get("/loans/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(loanVOS)));
    }

    @Test
    public void getAggregateByLender() throws Exception{
        AggregateCO aggregateCO = AggregateCO.builder().interestPerDay(1).penaltyDay(1).remainingAmount(2000).build();

        Map<String, AggregateCO> map = new HashMap<>();
        map.put("len1", aggregateCO);
        map.put("len2", aggregateCO);

        when(loanService.getAggregateByLender()).thenReturn(map);
        mockMvc
                .perform(MockMvcRequestBuilders.get("/loans/aggregate/lender"))
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(map)));
    }

    @Test
    public void getAggregateByCustomer() throws Exception{
        AggregateCO aggregateCO = AggregateCO.builder().interestPerDay(1).penaltyDay(1).remainingAmount(2000).build();

        Map<String, AggregateCO> map = new HashMap<>();
        map.put("C1", aggregateCO);
        map.put("C2", aggregateCO);

        when(loanService.getAggregateByCustomer()).thenReturn(map);
        mockMvc
                .perform(MockMvcRequestBuilders.get("/loans/aggregate/customer"))
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(map)));
    }

    @Test
    public void getAggregateByInterest() throws Exception{
        AggregateCO aggregateCO = AggregateCO.builder().interestPerDay(1).penaltyDay(1).remainingAmount(200).build();

        Map<Double, AggregateCO> map = new HashMap<>();
        map.put(1d, aggregateCO);
        map.put(2d, aggregateCO);

        when(loanService.getAggregateByInterest()).thenReturn(map);
        mockMvc
                .perform(MockMvcRequestBuilders.get("/loans/aggregate/interest"))
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(map)));
    }
}