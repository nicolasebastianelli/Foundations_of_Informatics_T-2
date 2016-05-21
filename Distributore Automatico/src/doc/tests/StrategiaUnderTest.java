package doc.tests;

import java.util.Date;
import java.util.Map;

import doc.model.DentoStrategiaConfigurabile;

public class StrategiaUnderTest extends DentoStrategiaConfigurabile {

	private static final long serialVersionUID = 1L;
	private Date now;

	public StrategiaUnderTest(String nome,
			Map<String, Integer> punteggiBevande, int ore, Date now) {
		super(nome, punteggiBevande, ore);
		this.now = now;
	}
	
	@Override
	protected Date getNow() {
		return now;
	}

}
