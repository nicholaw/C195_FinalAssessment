package database;

//import java.util.Set;

public enum DBTables {
    APPOINTMENT_TABLE("appointments"),
    CONTACT_TABLE("contacts"),
    COUNTRY_TABLE("countries"),
    CUSTOMER_TABLE("customers"),
    DIVISION_TABLE("first_level_divisions"),
    USER_TABLE("users");

    private final String tableName;
    //private final Set<String> columns;

    DBTables(String tableName/*, Collection<String> coll*/) {
        this.tableName = tableName;
        //this.columns = new HashSet<>(coll);
    }

    public String getTableName() {
        return this.tableName;
    }

    @Override
    public String toString() {
        return this.tableName;
    }
}