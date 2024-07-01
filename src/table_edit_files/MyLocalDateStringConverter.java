package table_edit_files;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.util.StringConverter;

public class MyLocalDateStringConverter extends StringConverter<LocalDate> {

	private final DateTimeFormatter formatter;

	public MyLocalDateStringConverter(String pattern) {
		this.formatter = DateTimeFormatter.ofPattern(pattern);
	}

	@Override
	public String toString(LocalDate date) {
		if (date != null) {
			return date.format(formatter);
		}
		return "";
	}

	@Override
	public LocalDate fromString(String value) {
		if (value != null && !value.isEmpty()) {
			try {
				return LocalDate.parse(value, formatter);
			} catch (DateTimeParseException e) {
				return null;
			}
		}
		return null;
	}
}
