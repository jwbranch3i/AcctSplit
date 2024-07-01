package test.table_edit_files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.junit.Test;

import table_edit_files.MyLocalDateStringConverter;

public class MyLocalDateStringConverterTest {

    @Test
    public void testFromString() {
        MyLocalDateStringConverter converter = new MyLocalDateStringConverter("MM/dd/yyyy");

        // Test valid date string
        String validDateString = "01/01/2022";
        LocalDate expectedDate = LocalDate.of(2022, 1, 1);
        LocalDate actualDate = converter.fromString(validDateString);
        assertEquals(expectedDate, actualDate);

        // Test invalid date string
        String invalidDateString = "2022-01-01";
        LocalDate actualDate2 = converter.fromString(invalidDateString);
        assertNull(actualDate2);

        // Test empty date string
        String emptyDateString = "";
        LocalDate expectedDate3 = null; // or any other expected behavior
        LocalDate actualDate3 = converter.fromString(emptyDateString);
        assertEquals(expectedDate3, actualDate3);
        assertNull(actualDate3);
    }

}