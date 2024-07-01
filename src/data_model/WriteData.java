package data_model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import application.MyUtil;
import transaction_objects.Transaction;

public class WriteData {
	private WriteData() {
	}

	/*******************************************************************/
	public static Boolean updateRecord(String cField, Transaction transaction) {
		String updateStmt;
		PreparedStatement psUpdateRecord;
		try {
			switch (cField) {
				case DB.COL_TRANSACTIONS_DATE:
					updateStmt = DB.sql_stmt_UPDATEFIELD_pt1 + cField + DB.sql_stmt_UPDATEFIELD_pt2;
					psUpdateRecord = DataSource.getConn().prepareStatement(updateStmt);
					psUpdateRecord.setString(1, MyUtil.localDateToString(transaction.getDate(), "yyyy-MM-dd"));
					psUpdateRecord.setInt(2, transaction.getId());

					psUpdateRecord.executeUpdate();

					if (psUpdateRecord != null) {
						psUpdateRecord.close();
					}
					break;

				case DB.COL_TRANSACTIONS_DESCRIPTION:
					updateStmt = DB.sql_stmt_UPDATEFIELD_pt1 + cField + DB.sql_stmt_UPDATEFIELD_pt2;
					psUpdateRecord = DataSource.getConn().prepareStatement(updateStmt);
					psUpdateRecord.setString(1, transaction.getDescription());
					psUpdateRecord.setInt(2, transaction.getId());

					psUpdateRecord.executeUpdate();

					if (psUpdateRecord != null) {
						psUpdateRecord.close();
					}
					break;

				case DB.COL_TRANSACTIONS_GAS:
					updateStmt = DB.sql_stmt_UPDATEFIELD_pt1 + cField + DB.sql_stmt_UPDATEFIELD_pt2;
					psUpdateRecord = DataSource.getConn().prepareStatement(updateStmt);
					psUpdateRecord.setDouble(1, transaction.getGas());
					psUpdateRecord.setInt(2, transaction.getId());

					psUpdateRecord.executeUpdate();

					if (psUpdateRecord != null) {
						psUpdateRecord.close();
					}
					break;
				case DB.COL_TRANSACTIONS_SERVICE:
					updateStmt = DB.sql_stmt_UPDATEFIELD_pt1 + cField + DB.sql_stmt_UPDATEFIELD_pt2;
					psUpdateRecord = DataSource.getConn().prepareStatement(updateStmt);
					psUpdateRecord.setDouble(1, transaction.getService());
					psUpdateRecord.setInt(2, transaction.getId());

					psUpdateRecord.executeUpdate();

					if (psUpdateRecord != null) {
						psUpdateRecord.close();
					}
					break;

				case DB.COL_TRANSACTIONS_JOHN:
					updateStmt = DB.sql_stmt_UPDATEFIELD_pt1 + cField + DB.sql_stmt_UPDATEFIELD_pt2;
					psUpdateRecord = DataSource.getConn().prepareStatement(updateStmt);
					psUpdateRecord.setDouble(1, transaction.getJohn());
					psUpdateRecord.setInt(2, transaction.getId());

					psUpdateRecord.executeUpdate();

					if (psUpdateRecord != null) {
						psUpdateRecord.close();
					}
					break;

				case DB.COL_TRANSACTIONS_MEDICAL:
					updateStmt = DB.sql_stmt_UPDATEFIELD_pt1 + cField + DB.sql_stmt_UPDATEFIELD_pt2;
					psUpdateRecord = DataSource.getConn().prepareStatement(updateStmt);
					psUpdateRecord.setDouble(1, transaction.getMedical());
					psUpdateRecord.setInt(2, transaction.getId());

					psUpdateRecord.executeUpdate();

					if (psUpdateRecord != null) {
						psUpdateRecord.close();
					}
					break;

				case DB.COL_TRANSACTIONS_MISC:
					updateStmt = DB.sql_stmt_UPDATEFIELD_pt1 + cField + DB.sql_stmt_UPDATEFIELD_pt2;
					psUpdateRecord = DataSource.getConn().prepareStatement(updateStmt);
					psUpdateRecord.setDouble(1, transaction.getMisc());
					psUpdateRecord.setInt(2, transaction.getId());

					psUpdateRecord.executeUpdate();

					if (psUpdateRecord != null) {
						psUpdateRecord.close();
					}
					break;
				default:
					break;
			}
			return true;
		} catch (Exception e) {
			System.out.println("Update transactions exception: " + e.getMessage());
			return false;
		}
	}

	public static Boolean insertRecord(Transaction transaction) {
		String insertStmt;
		PreparedStatement psInsertRecord;
		int generatedId = -1;
		try {
			insertStmt = DB.sql_stmt_INSERTRECORD;
			psInsertRecord = DataSource.getConn().prepareStatement(insertStmt, Statement.RETURN_GENERATED_KEYS);
			
			psInsertRecord.setString(1, MyUtil.localDateToString(transaction.getDate(), "yyyy-MM-dd"));
			psInsertRecord.setString(2, transaction.getDescription());
			psInsertRecord.setDouble(3, transaction.getGas());
			psInsertRecord.setDouble(4, transaction.getService());
			psInsertRecord.setDouble(5, transaction.getJohn());
			psInsertRecord.setDouble(6, transaction.getMedical());
			psInsertRecord.setDouble(7, transaction.getMisc());

			System.out.println("Inserting record: " + psInsertRecord.toString());
			psInsertRecord.executeUpdate();

			ResultSet rs = psInsertRecord.getGeneratedKeys();
			if (rs.next()) {
				generatedId = rs.getInt(1);
				transaction.setId(generatedId);
			}

			if (psInsertRecord != null) {
				psInsertRecord.close();
			}
			return true;
		} catch (Exception e) {
			System.out.println("Insert transactions exception: " + e.getMessage());
			return false;
		}
	}

	public static Boolean deleteRecord(Transaction transaction) {
		String deleteStmt;
		PreparedStatement psDeleteRecord;
		try {
			deleteStmt = DB.sql_stmt_DELETERECORD;
			psDeleteRecord = DataSource.getConn().prepareStatement(deleteStmt);
			psDeleteRecord.setInt(1, transaction.getId());

			psDeleteRecord.executeUpdate();

			if (psDeleteRecord != null) {
				psDeleteRecord.close();
			}
			return true;
		} catch (Exception e) {
			System.out.println("Delete transactions exception: " + e.getMessage());
			return false;
		}
	}

}
