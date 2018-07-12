package sqlite.model;

import java.io.Serializable;

public class Expense implements Serializable {
    private int id;
    private String name;
    private double spent;
    private double limit;
    private String type;

    public Expense() {}

    public Expense(int id, String name, double spent, double limit, String type) {
        this.id = id;
        this.name = name;
        this.spent = spent;
        this.limit = limit;
        this.type = type;
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

    public double getSpent() {
        return spent;
    }

    public double getLimit() {
        return limit;
    }

    public String getType() {
        return type;
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

    public void setSpent(double spent) {
        this.spent = spent;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public void setType(String type) {
        this.type = type;
    }
}
