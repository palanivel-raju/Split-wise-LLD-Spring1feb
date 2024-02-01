package com.projects.split_wise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Expense extends BaseModel{
    private int amount;
    private String description;
    @OneToMany
    private List<UserExpense> userExpenses;
    @Enumerated(EnumType.ORDINAL)
    private ExpenseType expenseType;
}
