package table_edit_files;

import application.MyUtil;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import transaction_objects.Transaction;

public class DoubleEditingCell extends TableCell<Transaction, Double>
{

	private TextField textField;

	public DoubleEditingCell()
	{
	}

	@Override
	public void startEdit()
	{
		if (!isEmpty())
		{
			super.startEdit();
			createTextField();
			setText(null);
			setGraphic(textField);
			textField.selectAll();
		}
	}

	@Override
	public void cancelEdit()
	{
		super.cancelEdit();

		setText(MyUtil.doubleToString(getItem()));
		setGraphic(null);
	}

	@Override
	public void updateItem(Double item, boolean empty)
	{
		super.updateItem(item, empty);

		if (empty)
		{
			setText(MyUtil.doubleToString(item));
			setGraphic(null);
		}
		else
		{
			if (isEditing())
			{
				if (textField != null)
				{
					textField.setText(getString());
					// setGraphic(null);
				}
				setText(null);
				setGraphic(textField);
			}
			else
			{
				setText(getString());
				setGraphic(null);
				setStyle("-fx-font-size: 14px");
			}
		}
	}

	private void createTextField()
	{
		textField = new TextField(getString());
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		textField.setOnAction((e) -> commitEdit(MyUtil.stringToDouble(textField.getText())));
		textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
		{
			if (!newValue)
			{
				System.out.println("Commiting " + textField.getText());
				commitEdit(MyUtil.stringToDouble(textField.getText()));
			}
		});
	}

	private String getString()
	{
		return getItem() == null ? "" : MyUtil.doubleToString(getItem());
	}
}
