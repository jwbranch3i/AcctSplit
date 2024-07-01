package data_model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.MyUtil;
import transaction_objects.Transaction;

public class ReadData {
	private ReadData() {
	}

	/**********************************************************************/
	public static List<Transaction> getTransactions(int sortOrder) {
		String quaryStmt = DB.getTransactionsStmt(sortOrder);

		try (
				Statement statement = DataSource.getConn().createStatement();
				ResultSet results = statement.executeQuery(quaryStmt)) {

			List<Transaction> list = new ArrayList<Transaction>();
			while (results.next()) {
				Transaction transaction = new Transaction();
				transaction.setId(results.getInt(DB.INDEX_TRANSACTIONS_ID));
				transaction.setDate(MyUtil.stringToLocalDate(results.getString(DB.INDEX_TRANSACTIONS_DATE), "yyyy-MM-dd"));
				transaction.setDescription(results.getString(DB.INDEX_TRANSACTIONS_DESCRIPTION));
				transaction.setGas(results.getDouble(DB.INDEX_TRANSACTIONS_GAS));
				transaction.setService(results.getDouble(DB.INDEX_TRANSACTIONS_SERVICE));
				transaction.setJohn(results.getDouble(DB.INDEX_TRANSACTIONS_JOHN));
				transaction.setMedical(results.getDouble(DB.INDEX_TRANSACTIONS_MEDICAL));
				transaction.setMisc(results.getDouble(DB.INDEX_TRANSACTIONS_MISC));

				list.add(transaction);
			}
			
			// for (int i = 0; i < list.size(); i++) {
			// 	System.out.println(list.get(i).toString());
			// }
			return list;

		} catch (SQLException e) {
			System.out.println("Querey to get Transactions failed: " + e.getMessage());
			return new ArrayList<Transaction>();
		}
	}

	/**********************************************************************/
	public static List<Double> getTotals() {

		String quaryStmt = DB.getTotalsStmt();

		try (
				Statement statement = DataSource.getConn().createStatement();
				ResultSet results = statement.executeQuery(quaryStmt)) {
			ArrayList<Double> list = new ArrayList<Double>();
			while (results.next()) {
				list.add(results.getDouble(DB.COL_TRANSACTIONS_GAS));
				list.add(results.getDouble(DB.COL_TRANSACTIONS_SERVICE));
				list.add(results.getDouble(DB.COL_TRANSACTIONS_JOHN));
				list.add(results.getDouble(DB.COL_TRANSACTIONS_MEDICAL));
				list.add(results.getDouble(DB.COL_TRANSACTIONS_MISC));
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Querey to get Totals failed: " + e.getMessage());
			return new ArrayList<Double>();

		}
	}

}
