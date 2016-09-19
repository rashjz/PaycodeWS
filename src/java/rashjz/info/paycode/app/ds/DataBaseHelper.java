/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rashjz.info.paycode.app.ds;

 
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Mobby
 */
public class DataBaseHelper {

    private static PaycodeDataSource dataSource;

    public static void setDataSource(PaycodeDataSource dataSource) {
        DataBaseHelper.dataSource = dataSource;
    }

    public static Connection connect() throws SQLException {
        return dataSource.connect();
    }

}
