package com.projects.split_wise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class UserExpense extends BaseModel{
    @ManyToOne
    private User user;
    @ManyToOne
    private Expense expense;
    private int amount;
    @Enumerated(EnumType.ORDINAL)
    private UserExpenseType userExpenseType;
}
