package hibernate.dialect;

import org.hibernate.dialect.PostgreSQL9Dialect;
import java.sql.Types;

public class JSONBPostgreSQLDialect extends PostgreSQL9Dialect {
	
	public JSONBPostgreSQLDialect() {
	    super();
	    registerColumnType(Types.JAVA_OBJECT, "jsonb");
	  }
}
