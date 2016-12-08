package ru.vlasenko.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by andreyvlasenko on 04/12/16.
 */
public class User {

    @Id
    private String id;
    private String name;
    private String avatar;
    private Date dateRegistered;
    private Date lastVisitDate;
    private String role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateREgistered) {
        this.dateRegistered = dateREgistered;
    }

    public Date getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(Date lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", dateRegistered=" + dateRegistered +
                ", lastVisitDate=" + lastVisitDate +
                ", role='" + role + '\'' +
                '}';
    }
}
