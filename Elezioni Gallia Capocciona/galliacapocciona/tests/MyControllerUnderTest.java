package galliacapocciona.tests;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import galliacapocciona.model.Collegio;
import galliacapocciona.persistence.CollegiReader;
import galliacapocciona.ui.MyController;
import galliacapocciona.ui.UserInteractor;

public class MyControllerUnderTest extends MyController {

	public MyControllerUnderTest(UserInteractor userInteractor) {
		super(userInteractor);
	}
	
	@Override
	public void loadData(Reader reader, CollegiReader collegiReader) {
		// nop
	}
	
	@Override
	public int getSeggiMinimi() {
		return 10;
	}

	@Override
	public List<Collegio> getListaCollegi() {
		return new ArrayList<Collegio>();
	}

}
