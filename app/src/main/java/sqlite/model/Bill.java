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
    private boolean paid;

    public static final String CREATE_TABLE_BILLS =
            "CREATE TABLE " + BILLS_TABLE_NAME + "("
                    + COLUMN_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_BILL_NAME + " TEXT,"
                    + COLUMN_BILL_COST + " NUMERIC,"
                    + COLUMN_BILL_FUND + " NUMERIC,"
                    + COLUMN_BILL_DUE + " TEXT,"
                    + COLUMN_BILL_PAID + " INTEGER,"
                    + ")";

    public Bill() {}

    public Bill(int id, String name, double cost, double fund, String due, boolean paid) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.fund = fund;
        this.due = due;
        this.paid = paid;
    }

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
    public boolean getPaid() {
        return paid;
    }
}
