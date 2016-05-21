package galliacapocciona.model;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class CalcolatoreSeggi {
	
	private static final HashMap<String, CalcolatoreSeggi> calcolatoreSeggiMap = 
			new HashMap<String, CalcolatoreSeggi>();
	
	static {
		try {
			addCalcolatoreSeggi("proporzionale", new CalcolatoreSeggiProporzionale());
			addCalcolatoreSeggi("maggioritario", new CalcolatoreSeggiMaggioritario());
			addCalcolatoreSeggi("misto", new CalcolatoreSeggiMisto());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static CalcolatoreSeggi getInstance(String name) throws NoSuchAlgorithmException {
		String lowerCaseName = name.toLowerCase();
		if (calcolatoreSeggiMap.containsKey(lowerCaseName)) {
			return calcolatoreSeggiMap.get(lowerCaseName);
		} else {
			throw new NoSuchAlgorithmException(name);
		}
	}
	
	public static String[] getCalcolatoriSeggi() {
		return calcolatoreSeggiMap.keySet().toArray(new String[0]);
	}
	
	public static void addCalcolatoreSeggi(String nome, CalcolatoreSeggi algoritmo) {
		calcolatoreSeggiMap.put(nome, algoritmo);
	}
	
	public abstract Map<String, Integer> assegnaSeggi(int seggi, List<Collegio> listaCollegi);
}
