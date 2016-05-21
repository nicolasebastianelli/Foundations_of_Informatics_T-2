package pharmame.model;

import java.util.Collection;

public interface FarmacoFilterFactory {
	Filter<Farmaco> get(String name, String searchKey);
	Collection<String> getNames();
}
