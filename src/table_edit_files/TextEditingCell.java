package table_edit_files;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import transaction_objects.Transaction;

public class TextEditingCell extends TableCell<Transaction, String>
{

	private TextField textField;

	public TextEditingCell()
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

		setText(getItem());
		setGraphic(null);
	}

	@Override
	public void updateItem(String item, boolean empty)
	{
		super.updateItem(item, empty);

		if (empty)
		{
			setText(item);
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
		textField.setOnAction((e) -> commitEdit(textField.getText()));
		textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
		{
			if (!newValue)
			{
				System.out.println("Commiting " + textField.getText());
				commitEdit(textField.getText());
			}
		});
	}

	private String getString()
	{
		return getItem() == null ? "" : getItem();
	}
}
