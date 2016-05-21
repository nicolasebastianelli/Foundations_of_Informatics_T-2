package pharmame.model;

public interface Filter<T> {
	boolean filter(T element);
}
