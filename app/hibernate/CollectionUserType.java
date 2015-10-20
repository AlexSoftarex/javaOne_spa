package hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;

public abstract class CollectionUserType extends MutableUserType {

	@SuppressWarnings("unchecked")
	@Override
	public Object deepCopy(Object value) throws HibernateException {

		if (!(value instanceof Collection)) {
			return deepCopyValue(value);
		}

		Collection<?> collection = (Collection) value;
		Collection collectionClone = CollectionFactory.newInstance(collection
				.getClass());

		for (Object member : collection) {
			collectionClone.add(deepCopy(member));
		}

		return collectionClone;
	}

	protected abstract Object deepCopyValue(Object value);

	static final class CollectionFactory {

		@SuppressWarnings("unchecked")
		static <E, T extends Collection<E>> T newInstance(
				Class<T> collectionClass) {
			if (List.class.isAssignableFrom(collectionClass)) {
				return (T) new ArrayList<E>();
			} else if (Set.class.isAssignableFrom(collectionClass)) {
				return (T) new HashSet<E>();
			} else {
				throw new IllegalArgumentException(
						"Unsupported collection type: " + collectionClass);
			}
		}
	}
}
