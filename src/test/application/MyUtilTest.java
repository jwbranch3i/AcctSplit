package test.application;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import application.MyUtil;

import java.time.LocalDate;

public class MyUtilTest {

    @Test
    public void testStringToLocalDate() {
        String dateFormat = "YYYY-MM-dd";
        
        // Test case 1: Valid date string
        String dateString1 = "01/01/2022";
        LocalDate expectedDate1 = LocalDate.of(2022, 1, 1);
        LocalDate actualDate1 = MyUtil.stringToLocalDate(dateString1, dateFormat);
        assertEquals(expectedDate1, actualDate1);

        // Test case 2: Invalid date string
        String dateString2 = "13/32/2023"; // Invalid month and day
        LocalDate expectedDate2 = null; // Expected null for invalid date
        LocalDate actualDate2 = MyUtil.stringToLocalDate(dateString2, dateFormat);
        assertEquals(expectedDate2, actualDate2);

        // Test case 3: Empty date string
        String dateString3 = "";
        LocalDate expectedDate3 = null; // Expected null for empty date string
        LocalDate actualDate3 = MyUtil.stringToLocalDate(dateString3, dateFormat);
        assertEquals(expectedDate3, actualDate3);
    }

    @Test
    public void testDateToString() {
        // Test case 1: Valid LocalDate
        LocalDate date1 = LocalDate.of(2022, 1, 1);
        String expectedString1 = "01/01/2022";
        String actualString1 = MyUtil.dateToString(date1);
        assertEquals(expectedString1, actualString1);

        // Test case 2: Null LocalDate
        LocalDate date2 = null;
        String expectedString2 = null;
        String actualString2 = MyUtil.dateToString(date2);
        assertEquals(expectedString2, actualString2);
    }

    @Test
    public void testLocalDateToString() {
        // Test case 1: Valid LocalDate and date format
        LocalDate date1 = LocalDate.of(2022, 1, 1);
        String dateFormat1 = "dd/MM/yyyy";
        String expectedString1 = "01/01/2022";
        String actualString1 = MyUtil.localDateToString(date1, dateFormat1);
        assertEquals(expectedString1, actualString1);

        // Test case 2: Null LocalDate and valid date format
        LocalDate date2 = null;
        String dateFormat2 = "dd/MM/yyyy";
        String expectedString2 = null;
        String actualString2 = MyUtil.localDateToString(date2, dateFormat2);
        assertEquals(expectedString2, actualString2);

        // Test case 3: Valid LocalDate and empty date format
        LocalDate date3 = LocalDate.of(2022, 1, 1);
        String dateFormat3 = "";
        String expectedString3 = "2022-01-01"; // Default format when empty
        String actualString3 = MyUtil.localDateToString(date3, dateFormat3);
        assertEquals(expectedString3, actualString3);
    }

    @Test
    public void testDoubleToString() {
        // Test case 1: Valid double
        double num1 = 10.5;
        String expectedString1 = "10.5";
        String actualString1 = MyUtil.doubleToString(num1);
        assertEquals(expectedString1, actualString1);

        // Test case 2: NaN
        double num2 = Double.NaN;
        String expectedString2 = "NaN";
        String actualString2 = MyUtil.doubleToString(num2);
        assertEquals(expectedString2, actualString2);
    }

    @Test
    public void testStringToDouble() {
        // Test case 1: Valid string representation of double
        String stringNum1 = "10.5";
        double expectedNum1 = 10.5;
        double actualNum1 = MyUtil.stringToDouble(stringNum1, null);
        assertEquals(expectedNum1, actualNum1, 0.0001);

        // Test case 2: Invalid string representation of double
        String stringNum2 = "abc";
        double expectedNum2 = 0.0; // Expected 0.0 for invalid string
        double actualNum2 = MyUtil.stringToDouble(stringNum2, null);
        assertEquals(expectedNum2, actualNum2, 0.0001);
    }

}
