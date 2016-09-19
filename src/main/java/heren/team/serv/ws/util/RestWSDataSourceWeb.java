package heren.team.serv.ws.util;

import az.gov.mia.mtp.helper.db.MtpDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
//import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 *
 * @author Vusal Hasanli
 */
public class RestWSDataSourceWeb extends RestWSDataSource {

    private static final Logger logger = Logger.getLogger(RestWSDataSourceWeb.class.getName());
    
    private final String jndiName;
    
    public RestWSDataSourceWeb(String jndiName) {
        this.jndiName = jndiName;
    }
    
    @Override
    public Connection connect() throws SQLException {
        Connection connection = null;
        Locale def = Locale.getDefault();
        try {
            
            Locale.setDefault(new Locale("en", "US"));
            Context initContext = new InitialContext();
//            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) initContext.lookup("restoranJND"); 
            connection = ds.getConnection();
            connection.setAutoCommit(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Locale.setDefault(def);
        }
        return connection;
    }
    
    public static void main(String[] args) throws SQLException {
        Connection c = new RestWSDataSourceWeb("").connect();
        System.out.println(c);
    }
    
}
