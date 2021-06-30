package database;

/**
 *
 */
public enum CustomerColumns {
    CUSTOMER_ID("Customer_ID"),
    CUSTOMER_NAME("Customer_Name"),
    CUSTOMER_ADDRESS("Address"),
    CUSTOMER_CITY("City"),
    CUSTOMER_POSTAL_CODE("Postal_Code"),
    CUSTOMER_PHONE("Phone"),
    CUSTOMER_CREATE_DATE("Create_Date"),
    CUSTOMER_CREATE_BY("Created_By"),
    CUSTOMER_LAST_UPDATE("Last_Update"),
    CUSTOMER_UPDATE_BY("Last_Updated_By"),
    CUSTOMER_DIVISION_ID("Division_ID");

    private final String colName;

    CustomerColumns(String colName) {
        this.colName = colName;
    }

    /**
     * Returns true if there is a customer column whose name equals the given string. Used for
     * sanitizing prepared statements.
     *
     * @param str
     * @return
     */
    public static boolean contains(String str) {
        for(CustomerColumns col : CustomerColumns.values()) {
            if(col.getColName().equals(str))
                return true;
        }
        return false;
    }//contains

    public String getColName() {
        return this.colName;
    }

    @Override
    public String toString() {
        return this.colName;
    }
}