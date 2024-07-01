package data_model;

public class DB {
	/* table - transactions */
	public static final String TABLE_TRANSACTIONS = "Transactions";
	public static final String COL_TRANSACTIONS_ID = "_id";
	public static final String COL_TRANSACTIONS_DATE = "date";
	public static final String COL_TRANSACTIONS_DESCRIPTION = "description";
	public static final String COL_TRANSACTIONS_GAS = "gas";
	public static final String COL_TRANSACTIONS_SERVICE = "service";
	public static final String COL_TRANSACTIONS_JOHN = "john";
	public static final String COL_TRANSACTIONS_MEDICAL = "medical";
	public static final String COL_TRANSACTIONS_MISC = "misc";
	public static final String COL_TRANSACTIONS_TAX = "tax";
	public static final String COL_TRANSACTIONS_SAVINGS = "savings";

	public static final int INDEX_TRANSACTIONS_ID = 1;
	public static final int INDEX_TRANSACTIONS_DATE = 2;
	public static final int INDEX_TRANSACTIONS_DESCRIPTION = 3;
	public static final int INDEX_TRANSACTIONS_GAS = 4;
	public static final int INDEX_TRANSACTIONS_SERVICE = 5;
	public static final int INDEX_TRANSACTIONS_JOHN = 6;
	public static final int INDEX_TRANSACTIONS_MEDICAL = 7;
	public static final int INDEX_TRANSACTIONS_MISC = 8;
	public static final int INDEX_TRANSACTIONS_TAX = 9;
	public static final int INDEX_TRANSACTIONS_SAVINGS = 10;

	public static final int ORDER_BY_NONE = 1;
	public static final int ORDER_BY_ASC = 2;
	public static final int ORDER_BY_DESC = 3;

	public static final String sql_stmt_UPDATEFIELD_pt1 = "UPDATE Transactions set ";
	public static final String sql_stmt_UPDATEFIELD_pt2 = " = ? WHERE _id = ?";
	public static final String sql_stmt_INSERTRECORD = "INSERT INTO Transactions (date, description, gas, service, john, medical, misc) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?)";
	public static final String sql_stmt_DELETERECORD = "DELETE FROM Transactions WHERE _id = ?";

	public static String getTransactionsStmt(int sortOrder) {
		StringBuilder sb = new StringBuilder("SELECT ");
		sb.append(COL_TRANSACTIONS_ID + ", ");
		sb.append(COL_TRANSACTIONS_DATE + ", ");
		sb.append(COL_TRANSACTIONS_DESCRIPTION + ", ");
		sb.append(COL_TRANSACTIONS_GAS + ", ");
		sb.append(COL_TRANSACTIONS_SERVICE + ", ");
		sb.append(COL_TRANSACTIONS_JOHN + ", ");
		sb.append(COL_TRANSACTIONS_MEDICAL + ", ");
		sb.append(COL_TRANSACTIONS_MISC + " ");
		sb.append("FROM " + TABLE_TRANSACTIONS);

		if (sortOrder != DB.ORDER_BY_NONE) {
			sb.append(" ORDER BY ");
			sb.append(DB.COL_TRANSACTIONS_DATE);
			if (sortOrder == DB.ORDER_BY_DESC) {
				sb.append(" DESC");
			} else {
				sb.append(" ASC");
			}
		}

		return sb.toString();
	}

	public static String getTotalsStmt() {
		StringBuilder sb = new StringBuilder("SELECT ");
		sb.append("sum(" + COL_TRANSACTIONS_GAS + ") as gas, ");
		sb.append("sum(" + COL_TRANSACTIONS_SERVICE + ") as service, ");
		sb.append("sum(" + COL_TRANSACTIONS_JOHN + ") as john, ");
		sb.append("sum(" + COL_TRANSACTIONS_MEDICAL + ") as medical, ");
		sb.append("sum(" + COL_TRANSACTIONS_MISC + ") as misc ");
		sb.append("FROM " + TABLE_TRANSACTIONS);

		return sb.toString();
	}

}
