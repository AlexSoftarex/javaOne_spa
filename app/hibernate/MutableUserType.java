package hibernate;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.type.SerializationException;
import org.hibernate.usertype.UserType;

public abstract class MutableUserType implements UserType {

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) {
			return true;
		}
		if ((x == null) || (y == null)) {
			return false;
		}
		return false;//x.equals(y);
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		assert (x != null);
		return x.hashCode();
	}

	@Override
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return deepCopy(cached);
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {

		Object deepCopy = deepCopy(value);

		if (!(deepCopy instanceof Serializable)) {
			throw new SerializationException(String.format(
					"deepCopy of %s is not serializable", value), null);
		}

		return (Serializable) deepCopy;
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return deepCopy(original);
	}
}
