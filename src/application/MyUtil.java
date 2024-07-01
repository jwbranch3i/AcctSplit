package application;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MyUtil {
	private MyUtil() {
	}

	static String dateFormat = "MM/dd/yyyy";

	public static LocalDate stringToLocalDate(String inDateString, String dateFormat) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
			return LocalDate.parse(inDateString, formatter);
		} catch (Exception e) {
			return null;
		}
	}

	public static String dateToString(LocalDate inStringDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(inStringDate);
	}

	public static String localDateToString(LocalDate inDate, String dateFormat) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
			return inDate.format(formatter);
		} catch (Exception e) {
			return null;
		}
	}

	public static String doubleToString(Double num) {
		if (num != null) {
			return String.format("%.2f", num);
		} else
			return "";
	}

	/**
	 * Converts a string representation of a number to a double value.
	 * 
	 * @param stringNum  the string representation of the number
	 * @param errorValue the value to return if the conversion fails
	 * @return the double value of the string representation, or the error value if
	 *         the conversion fails
	 */
	public static Double stringToDouble(String stringNum, Double errorValue) {
		try {
			return Double.parseDouble(stringNum);
		} catch (NumberFormatException e) {
			return errorValue;
		}
	}
}
