package hibernate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.postgresql.util.PGobject;
import play.libs.Json;

public class JSONBUserType extends CollectionUserType implements
		ParameterizedType {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final String JSONB_TYPE = "jsonb";
	public static final String CLASS = "CLASS";
	
	@Override
	public Class<Object> returnedClass() {
		return Object.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.JAVA_OBJECT };
	}

	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		
		final String json = resultSet.getString(names[0]);
		return json == null ? null : Json.parse(json);
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		try {
			final String json = value == null ? null : MAPPER
					.writeValueAsString(value);

			PGobject pgo = new PGobject();
			pgo.setType(JSONB_TYPE);
			pgo.setValue(json);
			st.setObject(index, pgo);
		} catch (JsonProcessingException ex) {
			throw new HibernateException(ex);
		}
	}

	@Override
	protected Object deepCopyValue(Object value) {
		return value;
	}

	@Override
	public void setParameterValues(Properties parameters) {
		
	}
}
