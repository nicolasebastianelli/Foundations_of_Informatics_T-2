package trains.ui;

import java.time.LocalDate;
import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import trains.util.DateConverter;

public class TrainDateSpinner extends JSpinner {

	private static final long serialVersionUID = 1L;

	public TrainDateSpinner() {
		super(new SpinnerDateModel());
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(this,
				"E dd/MM/yyyy");
		setEditor(dateEditor);

	}

	public LocalDate getDateValue() {
		return DateConverter.asLocalDate((Date) getValue());

	}

}
