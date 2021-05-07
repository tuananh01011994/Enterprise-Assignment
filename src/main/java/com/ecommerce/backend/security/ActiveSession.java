package com.ecommerce.backend.security;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.List;

public class ActiveSession implements HttpSessionBindingListener {
    private String email;
    private ActiveSessionList activeSessionList;

    public ActiveSession(String email, ActiveSessionList activeUserStore) {
        this.email = email;
        this.activeSessionList = activeUserStore;
    }
    public void valueBound(HttpSessionBindingEvent event) {
        List<String> users = activeSessionList.getUsers();
        ActiveSession user = (ActiveSession) event.getValue();
        if (!users.contains(user.getEmail())) {
            users.add(user.getEmail());
        }
        System.out.println(activeSessionList.toString());
    }

    public void valueUnbound(HttpSessionBindingEvent event) {
        List<String> users = activeSessionList.getUsers();
        ActiveSession user = (ActiveSession) event.getValue();

        users.remove(user.getEmail());
        System.out.println(activeSessionList.toString());
    }

    public String getEmail() {
        return email;
    }

    public ActiveSessionList getActiveSessionList() {
        return activeSessionList;
    }
}

