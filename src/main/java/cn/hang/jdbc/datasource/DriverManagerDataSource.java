package cn.hang.jdbc.datasource;

import cn.hang.core.reflection.Classes;
import cn.hang.core.util.Strings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static com.google.common.base.Preconditions.checkState;

public class DriverManagerDataSource extends AbstractDriverBasedDataSource {

    /**
     * Constructor for bean-style configuration.
     */
    public DriverManagerDataSource() {
    }

    /**
     * Create a new DriverManagerDataSource with the given JDBC URL,
     * not specifying a username or password for JDBC access.
     * @param url the JDBC URL to use for accessing the DriverManager
     * @see java.sql.DriverManager#getConnection(String)
     */
    public DriverManagerDataSource(String url) {
        setUrl(url);
    }

    /**
     * Create a new DriverManagerDataSource with the given standard
     * DriverManager parameters.
     * @param url the JDBC URL to use for accessing the DriverManager
     * @param username the JDBC username to use for accessing the DriverManager
     * @param password the JDBC password to use for accessing the DriverManager
     * @see java.sql.DriverManager#getConnection(String, String, String)
     */
    public DriverManagerDataSource(String url, String username, String password) {
        setUrl(url);
        setUsername(username);
        setPassword(password);
    }

    /**
     * Create a new DriverManagerDataSource with the given JDBC URL,
     * not specifying a username or password for JDBC access.
     * @param url the JDBC URL to use for accessing the DriverManager
     * @param conProps JDBC connection properties
     * @see java.sql.DriverManager#getConnection(String)
     */
    public DriverManagerDataSource(String url, Properties conProps) {
        setUrl(url);
        setConnectionProperties(conProps);
    }

    /**
     * Create a new DriverManagerDataSource with the given standard
     * DriverManager parameters.
     * @param driverClassName the JDBC driver class name
     * @param url the JDBC URL to use for accessing the DriverManager
     * @param username the JDBC username to use for accessing the DriverManager
     * @param password the JDBC password to use for accessing the DriverManager
     * @deprecated since Spring 2.5. DriverManagerDataSource is primarily
     * intended for accessing <i>pre-registered</i> JDBC drivers.
     * If you need to register a new driver, consider using
     */
    @Deprecated
    public DriverManagerDataSource(String driverClassName, String url, String username, String password) {
        setDriverClassName(driverClassName);
        setUrl(url);
        setUsername(username);
        setPassword(password);
    }


    /**
     * Set the JDBC driver class name. This driver will get initialized
     * on startup, registering itself with the JDK's DriverManager.
     * The "driverClassName" property is mainly preserved for backwards compatibility,
     * as well as for migrating between Commons DBCP and this DataSource.
     * @see java.sql.DriverManager#registerDriver(java.sql.Driver)
     */
    public void setDriverClassName(String driverClassName) {
        checkState(!Strings.isNullOrEmpty(driverClassName), "Property 'driverClassName' must not be empty");
        String driverClassNameToUse = driverClassName.trim();
        try {
            Class.forName(driverClassNameToUse, true, Classes.getDefaultClassLoader());
        }
        catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Could not load JDBC driver class [" + driverClassNameToUse + "]", ex);
        }
    }


    @Override
    protected Connection getConnectionFromDriver(Properties props) throws SQLException {
        return getConnectionFromDriverManager(getUrl(), props);
    }

    /**
     * Getting a Connection using the nasty static from DriverManager is extracted
     * into a protected method to allow for easy unit testing.
     * @see java.sql.DriverManager#getConnection(String, java.util.Properties)
     */
    protected Connection getConnectionFromDriverManager(String url, Properties props) throws SQLException {
        return DriverManager.getConnection(url, props);
    }

}
