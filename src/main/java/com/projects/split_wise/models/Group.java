package com.projects.split_wise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class Group extends BaseModel{
    private String groupName;
    @ManyToMany
    private List<User> users;
    @OneToMany
    private List<Expense> expenses;
}
