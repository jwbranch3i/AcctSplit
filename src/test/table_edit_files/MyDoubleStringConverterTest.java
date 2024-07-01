package test.table_edit_files;

import org.junit.Test;

import table_edit_files.MyDoubleStringConverter;

import static org.junit.Assert.*;

public class MyDoubleStringConverterTest {

    @Test
    public void testFromString() {
        MyDoubleStringConverter converter = new MyDoubleStringConverter();

        // Test converting integer string
        String validIntegerString = "123";
        Double expectedInteger = 123.0;
        assertEquals(expectedInteger, converter.fromString(validIntegerString));

        // Test converting valid double string
        String validDoubleString = "3.14";
        Double expectedDouble = 3.14;
        assertEquals(expectedDouble, converter.fromString(validDoubleString));

        // Test converting invalid double string
        String invalidDoubleString = "abc";
        assertNull(converter.fromString(invalidDoubleString));
    }

}
