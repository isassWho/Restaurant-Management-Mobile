package com.cod.tablayout_demo.entities;

import java.io.Serializable;

public class Reservation implements Serializable {

    private static final Long serialVersionUID = 300L;

    private Integer id;
    private String date;
    private String hour;
    private String accountOwner;
    private String noAdults;
    private String noChildren;
    private String status;
    private String comments;
    private boolean is_reservation;
    private String phone;

    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public String getNoAdults() {
        return noAdults;
    }

    public void setNoAdults(String noAdults) {
        this.noAdults = noAdults;
    }

    public String getNoChildren() {
        return noChildren;
    }

    public void setNoChildren(String noChildren) {
        this.noChildren = noChildren;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isReservation() {
        return is_reservation;
    }

    public void setReservation(boolean is_reservation) {
        this.is_reservation = is_reservation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", accountOwner='" + accountOwner + '\'' +
                ", noAdults='" + noAdults + '\'' +
                ", noChildren='" + noChildren + '\'' +
                ", status='" + status + '\'' +
                ", comments='" + comments + '\'' +
                ", is_reservation=" + is_reservation +
                ", phone='" + phone + '\'' +
                '}';
    }
}
