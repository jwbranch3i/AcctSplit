package table_edit_files;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import application.MyUtil;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import transaction_objects.Transaction;

public class DateEditingCell extends TableCell<Transaction, Date>
{

	private DatePicker datePicker;

	public DateEditingCell()
	{
	}

	@Override
	public void startEdit()
	{
		if (!isEmpty())
		{
			super.startEdit();
			createDatePicker();
			setText(null);
			setGraphic(datePicker);
		}
	}

	@Override
	public void cancelEdit()
	{
		super.cancelEdit();

		setText(getDate().toString());
		setGraphic(null);
	}

	@Override
	public void updateItem(Date item, boolean empty)
	{
		super.updateItem(item, empty);

		if (empty)
		{
			setText(null);
			setGraphic(null);
		}
		else
		{
			if (isEditing())
			{
				if (datePicker != null)
				{
					datePicker.setValue(getDate());
				}
				setText(null);
				setGraphic(datePicker);
			}
			else
			{
				setText(MyUtil.localDateToString(getDate(), "dd/MM/yyyy"));
				setStyle("-fx-font-size: 14px");
				setGraphic(null);
			}
		}
	}

	private void createDatePicker()
	{
		datePicker = new DatePicker(getDate());
		datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		datePicker.setOnAction((e) ->
		{
			System.out.println("Committed: " + datePicker.getValue().toString());
			commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		});
	}

	private LocalDate getDate()
	{
		return getItem() == null ? LocalDate.now() : getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
