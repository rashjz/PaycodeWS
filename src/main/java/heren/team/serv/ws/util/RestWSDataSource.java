package heren.team.serv.ws.util;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Vusal Hasanli
 */
public abstract class RestWSDataSource {
    public abstract Connection connect() throws SQLException;
}
