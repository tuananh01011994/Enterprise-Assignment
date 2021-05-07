package com.ecommerce.backend.security;

import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class ActiveSessionList {

    public List<String> users;


    public ActiveSessionList() {
        users = new ArrayList<>();
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "ActiveSessionList{" +
                "users=" + users +
                '}';
    }
}
