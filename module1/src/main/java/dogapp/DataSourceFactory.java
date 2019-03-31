package dogapp;

import org.springframework.beans.factory.FactoryBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DataSourceFactory implements FactoryBean<DataSource> {
    @Override
    public DataSource getObject() throws Exception {
        Context intiContext = new InitialContext();
        Context context = (Context) intiContext.lookup("java:comp/env");
        return (DataSource) context.lookup("jdbc/myPostgres");
    }

    @Override
    public Class<?> getObjectType() {
        return DataSource.class;
    }
}
