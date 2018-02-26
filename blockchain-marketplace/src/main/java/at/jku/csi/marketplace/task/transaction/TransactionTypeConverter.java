package at.jku.csi.marketplace.task.transaction;

import java.beans.PropertyEditorSupport;

public class TransactionTypeConverter extends PropertyEditorSupport {

	public void setAsText(final String text) throws IllegalArgumentException {
		setValue(TransactionType.fromValue(text));
	}
}
