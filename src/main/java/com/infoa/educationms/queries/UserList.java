package com.infoa.educationms.queries;

import com.infoa.educationms.entities.User;

import java.util.List;

public class UserList {
    private int count;
    private List<User> users;

    public UserList(List<User> users) {
        this.count = users.size();
        this.users = users;
    }
    public int getCount() {return count;}
    public void setCount(int count) {this.count = count;}

    public List<User> getCards() {return users;}
    public void setCards(List<User> users) {this.users = users;}
}
