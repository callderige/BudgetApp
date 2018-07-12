package sqlite.model;

import java.io.Serializable;

public class Bill implements Serializable{
    private int id;
    private String name;
    private double cost;
    private double fund;
    private String due;
    private int paid;

    public Bill() {}

    public Bill(int id, String name, double cost, double fund, String due, int paid) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.fund = fund;
        this.due = due;
        this.paid = paid;
    }

    /*
        Get methods
     */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public double getFund() {
        return fund;
    }

    public String getDue() {
        return due;
    }

    public int getPaid() {
        return paid;
    }

    /*
        Set methods
     */
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setFund(double fund) {
        this.fund = fund;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }
}
