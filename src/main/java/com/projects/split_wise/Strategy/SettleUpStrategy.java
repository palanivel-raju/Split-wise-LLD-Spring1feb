package com.projects.split_wise.Strategy;

import com.projects.split_wise.models.Expense;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SettleUpStrategy {
    public List<Expense> settleUp(List<Expense> expenses);
}