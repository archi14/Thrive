package models;

import enums.Role;

import java.util.UUID;

public class User {
    String user_id;
    String name;
    Role role;

    public User(String name, Role role) {
        this.user_id = UUID.randomUUID().toString();
        this.name = name;
        this.role = role;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                '}';
    }
}
