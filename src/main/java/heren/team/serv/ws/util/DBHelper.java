package heren.team.serv.ws.util;

 
import az.gov.mia.mtp.helper.db.MtpDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author Vusal Hasanli
 */
public class DBHelper {
    
    public static Connection connect() throws SQLException {        
        Connection connection = null;
        Locale def = Locale.getDefault();
        try {
            
            Locale.setDefault(new Locale("en", "US"));
            Context initContext = new InitialContext();
//            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) initContext.lookup("restoranJND");
//            logger.info("datasource is " + ds);
            connection = ds.getConnection();
            connection.setAutoCommit(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Locale.setDefault(def);
        }
        return connection;       
    }
        
    
    
        
//    public static Connection connect() throws SQLException {
//        Connection connection = null;
//        Locale def=Locale.getDefault();
//        try {
//            
//            Locale.setDefault(new Locale("en", "US"));            
//            Context initContext = new InitialContext();
////            Context envContext = (Context) initContext.lookup("java:/comp/env");
//            DataSource ds = (DataSource) initContext.lookup("jdbc/dbmtp");
//            connection = ds.getConnection();
//            connection.setAutoCommit(false);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            Locale.setDefault(def);
//        }
//        return connection;
//    }

    
    
//    public static void main(String[] args) {
//        try {
//            Connection c = DatabaseHelper.connect();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }


}
