package pharmame.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class FilterApplier {
	
	public static <T> List<T> applyFilter(Collection<T> input, Filter<T> filter) {
		ArrayList<T> output = new ArrayList<>();
		
		for (T element : input) {
			if (filter.filter(element)) {
				output.add(element);
			}
		}
		
		return output;
	}
	
}
