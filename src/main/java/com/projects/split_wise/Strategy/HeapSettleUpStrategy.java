package com.projects.split_wise.Strategy;


import com.projects.split_wise.models.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HeapSettleUpStrategy implements SettleUpStrategy{
    @Override
    public List<Expense> settleUp(List<Expense> expenses) {
        // 1. Iterate over the user
        Map<User, Integer> paidMap = new HashMap<>();
        Map<User, Integer> HadToMap = new HashMap<>();
        // 2. store in the map for paid and had to pay
        for(Expense expense : expenses){
            for(UserExpense userExpense : expense.getUserExpenses()){
                User user = userExpense.getUser();
                if(userExpense.getUserExpenseType().equals(UserExpenseType.Paid)){
                    if(paidMap.containsKey(user)){
                        paidMap.put(user, paidMap.get(user)+userExpense.getAmount());
                    }
                    paidMap.put(user, userExpense.getAmount());
                }
                else if(userExpense.getUserExpenseType().equals(UserExpenseType.HadToPay)){
                    if(HadToMap.containsKey(user)){
                        HadToMap.put(user, HadToMap.get(user)+userExpense.getAmount());
                    }
                    paidMap.put(user, userExpense.getAmount());
                }
            }
        }

        //3. effective amount stored in map
        //priority queue for user and amount
        PriorityQueue<Pair> pqPaid = new PriorityQueue<>((pair1, pair2)-> Integer.compare(pair2.getAmount(), pair1.getAmount()));
        PriorityQueue<Pair> pqhadToPay = new PriorityQueue<>((pair1, pair2)-> Integer.compare(pair2.getAmount(), pair1.getAmount()));
        for(Map.Entry<User, Integer> e : paidMap.entrySet()){
            pqPaid.add(new Pair(e.getKey(),e.getValue()));
        }
        for(Map.Entry<User, Integer> e : HadToMap.entrySet()){
            pqhadToPay.add(new Pair(e.getKey(),e.getValue()));
        }
        //paid-had to pay if +ve put in paid
        //amount if -ve to put in hadto pay
        //equal just remove
        while(pqPaid.isEmpty() && pqhadToPay.isEmpty()){
            Pair paid = pqPaid.poll();
            Pair HadToPay = pqhadToPay.poll();
            int amt = paid.getAmount()-HadToPay.getAmount();
            if(amt >0){
                pqPaid.add(new Pair(paid.getUser(), amt));
            }
            else if(amt<0){
                pqhadToPay.add(new Pair(paid.getUser(), amt));
            }
        }
        //store expense in transaction and return
        List<Expense> transactions = new ArrayList<>();
        List<UserExpense> revuserExpenses = new ArrayList<>();
        Expense expensePaid = new Expense();
        while(pqPaid.isEmpty()){
            UserExpense userExpense = new UserExpense();
            Pair pair = pqPaid.poll();
            userExpense.setAmount(pair.getAmount());
            userExpense.setUser(pair.getUser());
            userExpense.setExpense(expensePaid);
            revuserExpenses.add(userExpense);
        }
        expensePaid.setExpenseType(ExpenseType.Transaction);
        transactions.add(expensePaid);
        Expense expensehadtopay = new Expense();
        while(pqhadToPay.isEmpty()){
            UserExpense userExpense = new UserExpense();
            Pair pair = pqhadToPay.poll();
            userExpense.setAmount(pair.getAmount());
            userExpense.setUser(pair.getUser());
            userExpense.setExpense(expensehadtopay);
            revuserExpenses.add(userExpense);
        }
        expensehadtopay.setExpenseType(ExpenseType.Transaction);
        transactions.add(expensehadtopay);
        return transactions;
    }
}