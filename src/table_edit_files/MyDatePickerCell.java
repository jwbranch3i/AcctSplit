package table_edit_files;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

import java.time.LocalDate;

public class MyDatePickerCell<S, T> extends TableCell<S, T> {

    private DatePicker datePicker;

    public MyDatePickerCell() {
        this.datePicker = new DatePicker();
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            datePicker.setValue((LocalDate) item);
            setGraphic(datePicker);
        }
    }
}