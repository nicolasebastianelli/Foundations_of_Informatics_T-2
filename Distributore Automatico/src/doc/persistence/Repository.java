package doc.persistence;

import java.util.Collection;

public interface Repository<T> {
	Collection<T> getAll();
}
