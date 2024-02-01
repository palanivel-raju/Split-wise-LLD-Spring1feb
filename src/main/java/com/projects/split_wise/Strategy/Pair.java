package com.projects.split_wise.Strategy;


import com.projects.split_wise.models.ExpenseType;
import com.projects.split_wise.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pair {
    private User user;
    @Getter
    private int amount;
    private ExpenseType expenseType;
    public Pair(User user, int amount){
        this.user = user;
        this.amount = amount;

    }
}