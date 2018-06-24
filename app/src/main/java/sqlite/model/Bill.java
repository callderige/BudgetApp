package sqlite.model;

public class Bill {
    public static final String BILLS_TABLE_NAME = "bills";
    public static final String COLUMN_BILL_ID = "bill_id";
    public static final String COLUMN_BILL_NAME = "bill_name";
    public static final String COLUMN_BILL_COST = "bill_cost";
    public static final String COLUMN_BILL_FUND = "bill_fund";
    public static final String COLUMN_BILL_DUE = "bill_due";
    public static final String COLUMN_BILL_PAID = "bill_paid";

    private int id;
    private String name;
    private double cost;
    private double fund;
    private String due;
    private int paid;

    public static final String CREATE_TABLE_BILLS =
            "CREATE TABLE IF NOT EXISTS " + BILLS_TABLE_NAME + "("
                    + COLUMN_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_BILL_NAME + " TEXT,"
                    + COLUMN_BILL_COST + " INTEGER,"
                    + COLUMN_BILL_FUND + " INTEGER,"
                    + COLUMN_BILL_DUE + " TEXT,"
                    + COLUMN_BILL_PAID + " INTEGER"
                    + ")";

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
