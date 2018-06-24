package sqlite.model;

public class Expense {
    public static final String EXPENSES_TABLE_NAME = "expenses";
    public static final String COLUMN_EXPENSE_ID = "expense_id";
    public static final String COLUMN_EXPENSE_NAME = "expense_name";
    public static final String COLUMN_EXPENSE_SPENT = "expense_spent";
    public static final String COLUMN_EXPENSE_LIMIT = "expense_limit";
    public static final String COLUMN_EXPENSE_TYPE = "expense_type";

    private int id;
    private String name;
    private double spent;
    private double limit;
    private String type;

    public static final String CREATE_TABLE_EXPENSES =
            "CREATE TABLE IF NOT EXISTS " + EXPENSES_TABLE_NAME + "("
                    + COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_EXPENSE_NAME + " TEXT,"
                    + COLUMN_EXPENSE_SPENT + " NUMERIC,"
                    + COLUMN_EXPENSE_LIMIT + " NUMERIC,"
                    + COLUMN_EXPENSE_TYPE + " TEXT"
                    + ")";

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
