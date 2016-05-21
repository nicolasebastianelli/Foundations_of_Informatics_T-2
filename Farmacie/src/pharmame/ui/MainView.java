package pharmame.ui;

import java.util.Collection;
import java.util.List;

import pharmame.model.Farmaco;

public interface MainView {
	
	void setController(Controller c);

	void showMessage(String message);

	void setVisible(boolean visible);

	void setFilterNames(Collection<String> names);

	void setFarmaci(List<Farmaco> farmaci);
	
	void setOutput(String[] righe);
	
	Farmaco getFarmacoAt(int index);
	
}
