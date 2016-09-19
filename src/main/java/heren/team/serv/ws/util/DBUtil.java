package heren.team.serv.ws.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Vusal Hasanli
 */
public class DBUtil {

    public static void close(ResultSet rs, PreparedStatement ps, Connection con) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }

            if (ps != null) {
                ps.close();
            }

            if (con != null) {
                con.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void close(ResultSet rs, CallableStatement cs, Connection con) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }

            if (cs != null) {
                cs.close();
            }

            if (con != null) {
                con.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void close(ResultSet rs, ResultSet rsSecond, CallableStatement cs, Connection con) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (rsSecond != null && !rsSecond.isClosed()) {
                rsSecond.close();
            }

            if (cs != null) {
                cs.close();
            }

            if (con != null) {
                con.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void close(CallableStatement cs, Connection con) {
        try {

            if (cs != null) {
                cs.close();
            }

            if (con != null) {
                con.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void close(CallableStatement cs) {
        try {

            if (cs != null) {
                cs.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void rollback(Connection con) {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void commitRollback(boolean b, Connection con) {
        try {
            if (b) {
                if (con != null) {
                    con.commit();
                }
            } else {
                if (con != null) {
                    con.rollback();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
