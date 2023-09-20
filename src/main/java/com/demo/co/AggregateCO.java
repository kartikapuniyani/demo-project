package com.demo.co;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AggregateCO {

    private double remainingAmount;

    private double interestPerDay;

    private double penaltyDay;
}