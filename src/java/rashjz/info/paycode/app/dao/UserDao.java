/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rashjz.info.paycode.app.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.logging.Logger;
import rashjz.info.paycode.app.ds.DataBaseHelper;
import rashjz.info.paycode.app.ds.DatabaseUtil;
import rashjz.info.paycode.app.model.Balance;
import rashjz.info.paycode.app.model.User;
import rashjz.info.paycode.app.model.UserLog;

/**
 *
 * @author rasha_000
 */
public class UserDao {
    
    private static final Logger LOG = Logger.getLogger(UserDao.class.getName());
    
    public User checklogin(String login, String passw) {
//        login = "rashadjavad@gmail.com";
//        passw="12";
        Connection c = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = new User();
        try {
            c = DataBaseHelper.connect();
//            c = DatabaseUtil.connect();
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT u.ID,name,surname,patronymic,mobile, email ,balance \n"
                    + "                      FROM USERS u INNER JOIN USER_PASSWORD up ON u.ID = up.USER_ID \n"
                    + "                     WHERE u.EMAIL = ?  AND up.PASSWORD = ? AND u.STATUS = 'a'");
            
            pstm = c.prepareStatement(sql.toString());
            pstm.setString(1, login);
            pstm.setString(2, passw);
            rs = pstm.executeQuery();
            if (rs.next()) {
                user.setId(rs.getBigDecimal(1));
                user.setName(rs.getString(2));
                user.setSurname(rs.getString(3));
                user.setPatronymic(rs.getString(4));
                user.setMobile(rs.getString(5));
                user.setMobile(rs.getString(6));
                user.setBalance(rs.getBigDecimal(7));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(rs, pstm, c);
        }
        return user;
    }
    
    public Balance updateBalance(Balance balance) {
        LOG.info("updateBalance");
        Connection c = null;
        CallableStatement pstm = null;
        ResultSet rs = null;
        try {
            c = DataBaseHelper.connect();
//            c = DatabaseUtil.connect();
            StringBuffer sql = new StringBuffer();
            sql.append("{ call scanpay.update_balance (?     ,\n"
                    + "                          ?       ,\n"
                    + "                          ?         ,\n"
                    + "                          ?   ) }");
            
            pstm = c.prepareCall(sql.toString());
            pstm.setBigDecimal(1, balance.getUserID());
            pstm.setBigDecimal(2, balance.getAmount());
            pstm.registerOutParameter(3, Types.INTEGER);
            pstm.registerOutParameter(4, Types.INTEGER);
            int count = pstm.executeUpdate();            
            if (count > 0) {
                balance.setBalance(pstm.getBigDecimal(3));
                balance.setStatus(pstm.getBigDecimal(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(rs, pstm, c);
        }
        return balance;
    }
    
    public User registerUser(User user) {
        Connection c = null;
        CallableStatement pstm = null;
        ResultSet rs = null;
        
        try {
            c = DataBaseHelper.connect();
//            c = DatabaseUtil.connect();
            StringBuffer sql = new StringBuffer();
            sql.append("{ call scanpay.create_User (?     ,\n"
                    + "                          ?       ,\n"
                    + "                          ?          ,\n"
                    + "                          ?           ,\n"
                    + "                          ?         ,\n"
                    + "                          ?         ,\n"
                    + "                          ?   ) }");
            
            pstm = c.prepareCall(sql.toString());
            pstm.setString(1, user.getName());
            pstm.setString(2, user.getSurname());
            pstm.setString(3, user.getPatronymic());
            pstm.setString(4, user.getMobile());
            pstm.setString(5, user.getEmail());
            pstm.setString(6, user.getPassword());
            pstm.registerOutParameter(7, Types.INTEGER);
            
            int count = pstm.executeUpdate();
            
            if (count > 0) {
                user.setId(pstm.getBigDecimal(7));
            }
        } catch (Exception e) {
            user = new User();
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(rs, pstm, c);
        }
        return user;
    }
    
    public void insertUserLog(UserLog log) {
        Connection c = null;
        CallableStatement pstm = null;
        ResultSet rs = null;
        
        try {
            c = DataBaseHelper.connect();
//            c = DatabaseUtil.connect();
            StringBuffer sql = new StringBuffer();
            sql.append("{ call scanpay.user_logger (?     ,\n"
                    + "                          ?       ,\n"
                    + "                          ?          ,\n"
                    + "                          ?           ,\n"
                    + "                          ?   ) }");
            
            pstm = c.prepareCall(sql.toString());
            pstm.setString(1, log.getEmail());
            pstm.setString(2, log.getDevice_mac());
            pstm.setFloat(3, log.getAmount().floatValue());
            pstm.setString(4, log.getDevice_id());
            pstm.setString(5, log.getNote());
            int count = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(rs, pstm, c);
        }
    }
    
}
