package table_edit_files;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

public class DatePickerCell<S, T> extends TableCell<S, T>
{
    private final DatePicker datePicker;

    public DatePickerCell() {
        datePicker = new DatePicker();
        datePicker.setOnAction(event -> commitEdit((T) datePicker.getValue()));
        setGraphic(datePicker);
    }

    @Override
    protected void updateItem(T item, boolean empty)
    {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
			if (isEditing())
			{
				setContentDisplay(ContentDisplay.TEXT_ONLY);

			}
			else
			{
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				
				LocalDate temp = (LocalDate)item;
				
				String dateString = temp.format(formatter);
				
				setDatepikerDate(dateString);
				setText(dateString);
				setGraphic(this.datePicker);
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			}
        }
    }
    
	private void setDatepikerDate(String dateAsStr)
	{

		LocalDate ld = null;
		int jour, mois, annee;

		jour = mois = annee = 0;
		try
		{
			jour = Integer.parseInt(dateAsStr.substring(0, 2));
			mois = Integer.parseInt(dateAsStr.substring(3, 5));
			annee = Integer.parseInt(dateAsStr.substring(6, dateAsStr.length()));
		}
		catch (NumberFormatException e)
		{
			System.out.println("setDatepikerDate / unexpected error " + e);
		}

		ld = LocalDate.of(annee, mois, jour);
		datePicker.setValue(ld);
	}
    
}
