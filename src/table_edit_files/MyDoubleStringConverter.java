package table_edit_files;

import javafx.util.converter.DoubleStringConverter;

public class MyDoubleStringConverter extends DoubleStringConverter {

	public MyDoubleStringConverter() {
		super();
	}

	@Override
	public Double fromString(String value) {
		// catches the RuntimeException thrown by
		// DoubleStringConverter.fromString()
		try {
			return super.fromString(value);
		} catch (RuntimeException ex) {
			return null;
		}
	}
}