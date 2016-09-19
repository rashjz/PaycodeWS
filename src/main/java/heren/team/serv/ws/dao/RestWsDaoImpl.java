package heren.team.serv.ws.dao;

//import az.gov.mia.mtp.dao.jdbc.DatabaseHelper;
import az.gov.mia.mtp.dao.jdbc.helper.DatabaseHelper;
import az.gov.mia.mtp.domain.Announcement;
import az.gov.mia.mtp.domain.Constant;
import az.gov.mia.mtp.domain.ErrorModel;
import az.gov.mia.mtp.domain.Ixm;
import az.gov.mia.mtp.domain.Law;
import az.gov.mia.mtp.domain.ProtocolDetail;
import az.gov.mia.mtp.domain.ProtocolType;
import az.gov.mia.mtp.domain.hr.Structure;
import az.gov.mia.mtp.domain.security.User;
import az.gov.mia.mtp.helper.LogHelper;
import heren.team.serv.ws.domain.BallInfo;
import heren.team.serv.ws.domain.DecisionInfo;
import heren.team.serv.ws.domain.ProtocolDetailInfo;
import heren.team.serv.ws.domain.UnPaidProInfo;
import az.gov.mia.mtp.util.JdbcUtil;
import az.gov.mia.mtp.util.TypeUtil;
import heren.team.serv.ws.domain.Menu;
import heren.team.serv.ws.util.DBHelper;
import heren.team.serv.ws.util.DBUtil;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 *
 * @author Vusal Hasanli
 */
public class RestWsDaoImpl implements RestWsDao {

    private static final Logger logger = Logger.getLogger(RestWsDaoImpl.class);

    @Override
    public List<User> getUserListByStrId(BigDecimal strId) {
        List<User> userList = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            con = DatabaseHelper.connect();
            cs = con.prepareCall(SqlQuery.USER_LIST_BY_STR_ID);

            cs.registerOutParameter("p_ls", OracleTypes.CURSOR);
            cs.setBigDecimal("p_str_id", strId);
            cs.execute();

            rs = (ResultSet) cs.getObject("p_ls");
            if (rs != null) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getBigDecimal("id"));
                    user.setUserName(rs.getString("user_name"));
                    userList.add(user);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getUserListByStrId", e), Constant.MTP_MD_APP_ID);
        } finally {
            JdbcUtil.close(rs, cs, con);
        }
        return userList;
    }

    @Override
    public User loginUser(User user) {
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            con = DatabaseHelper.connect();
            cs = con.prepareCall(SqlQuery.USER_LOGIN);

            cs.setBigDecimal("p_id", user.getId());
            cs.setString("p_username", user.getUserName());
            cs.setString("p_password", user.getPassword());
            cs.setString("p_device_id", user.getParam().getDeviceId());
            cs.setString("p_gpsx", user.getParam().getGpsX());
            cs.setString("p_gpsy", user.getParam().getGpsY());
            cs.setString("p_gpsz", user.getParam().getGpsZ());
            cs.registerOutParameter("p_loginid", OracleTypes.NUMBER);
            cs.registerOutParameter("p_user", OracleTypes.CURSOR);
            cs.execute();

            rs = (ResultSet) cs.getObject("p_user");
            if (rs != null && rs.next()) {
                user.getParam().setLogInId(cs.getBigDecimal("p_loginid"));
                user.getEmployee().getStructure().setId(rs.getBigDecimal("str_id"));
                user.getEmployee().setEmpPositionId(rs.getBigDecimal("emppos_id"));
            }
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("loginUser", e, user.toString()), user);
        } finally {
            JdbcUtil.close(rs, cs, con);
        }
        return user;
    }

    @Override
    public List<ProtocolDetail> getNewRadarProListByCarNumber(String carNumber) {
        List<ProtocolDetail> protocolDetailList = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;

        ResultSet rs = null;
        try {
            con = DatabaseHelper.connect();
            cs = con.prepareCall(SqlQuery.GET_NEW_RADAR_PROTOCOL_DETAIL_lIST_BY_CAER_NUMBER);
            cs.registerOutParameter("p_ls", OracleTypes.CURSOR);

            cs.setString("p_veh_reg_number", carNumber);
            cs.execute();
            rs = (ResultSet) cs.getObject("p_ls");
            if (rs != null) {
                while (rs.next()) {
                    ProtocolDetail detail = new ProtocolDetail();
                    detail.setId(rs.getBigDecimal("id"));
                    detail.getProtocol().setSeries(rs.getString("p_series"));
                    detail.getProtocol().setNumber(rs.getString("p_number"));
                    detail.setCreateDate(rs.getTimestamp("create_date"));
                    detail.setActionLocation(rs.getString("action_location"));

                    detail.getCitizen().setName(rs.getString("c_name"));
                    detail.getCitizen().setSurname(rs.getString("c_surname"));
                    detail.getCitizen().setPatronymic(rs.getString("c_patronymic"));
                    detail.getCitizen().setRegAddress(rs.getString("c_reg_address"));

                    detail.getVehicle().setCarNumber(rs.getString("v_reg_number"));
                    detail.getVehicle().setMarka(rs.getString("v_marka"));

                    detail.getRadar().setId(rs.getBigDecimal("r_id"));
                    detail.getRadar().setStdNo(rs.getString("r_std_no"));
                    detail.getRadar().setCarNumber(rs.getString("r_car_number"));
                    detail.getRadar().setCarMarka(rs.getString("r_car_marka"));
                    detail.getRadar().setActionDate(rs.getTimestamp("r_action_date"));
                    detail.getRadar().getStdCaptureType().setId(rs.getBigDecimal("std_capture_type_id"));
                    detail.getRadar().setSpeedReal(rs.getInt("r_sp_real"));
                    detail.getRadar().setSpeedMax(rs.getInt("r_sp_max"));
                    detail.getRadar().setSpeedOver(rs.getInt("r_sp_over"));

                    detail.getRadar().getStd().setMarka(rs.getString("std_marka"));
                    detail.getRadar().getStd().setModel(rs.getString("std_model"));
                    detail.getRadar().getStd().setLastRegDate(rs.getTimestamp("std_last_regdate"));
                    detail.getRadar().getStd().setNextRegDate(rs.getTimestamp("std_next_regdate"));
                    detail.getRadar().getStd().setLastCode(rs.getString("std_last_code"));

                    Ixm ixm = new Ixm();
                    ixm.setAmount(rs.getDouble("ixm_amount"));
                    ixm.setDescription(rs.getString("ixm_description"));
                    ixm.setCodeAdditional(rs.getString("ixm_code_additional"));
                    detail.getIxmMap().put(ixm.getId(), ixm);

                    Announcement announcement = new Announcement();
                    announcement.setDeliveryDate(rs.getTimestamp("announ_delivery_date"));
                    announcement.setNote(rs.getString("announ_note"));
                    detail.getAnnouncementList().add(announcement);

                    protocolDetailList.add(detail);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getNewRadarProListByCarNumber", e), Constant.MTP_MD_APP_ID);
        } finally {
            JdbcUtil.close(rs, cs, con);
        }
        return protocolDetailList;
    }

    @Override
    public List<BallInfo> getBallInfoListByCarNumber(String carNumber) {
        List<BallInfo> ballProtocolList = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;

        ResultSet rs = null;
        try {
            con = DatabaseHelper.connect();
            cs = con.prepareCall(SqlQuery.GET_BALL_PROTOOL_DETAIL_lIST_BY_CAER_NUMBER);
            cs.registerOutParameter("p_ls", OracleTypes.CURSOR);

            cs.setString("p_veh_reg_number", carNumber);
            cs.execute();
            rs = (ResultSet) cs.getObject("p_ls");
            if (rs != null) {
                while (rs.next()) {
                    BallInfo ballProtocol = new BallInfo();
                    ballProtocol.setCarNumber(rs.getString("v_reg_number"));
                    ballProtocol.setpSeries(rs.getString("p_series"));
                    ballProtocol.setpNnumber(rs.getString("p_number"));
                    ballProtocol.setBall(rs.getInt("ball"));
                    ballProtocol.setCalDate(rs.getTimestamp("calc_date"));
                    ballProtocol.setExpireDate(rs.getTimestamp("expire_date"));

                    ballProtocolList.add(ballProtocol);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getBallInfoListByCarNumber", e), Constant.MTP_MD_APP_ID);
        } finally {
            JdbcUtil.close(rs, cs, con);
        }
        return ballProtocolList;
    }

    @Override
    public DecisionInfo getDecisionInfoByPdId(BigDecimal pdId) {
        DecisionInfo decisionInfo = new DecisionInfo();
        Connection con = null;
        CallableStatement cs = null;

        ResultSet rs = null;
        try {
            con = DatabaseHelper.connect();
            cs = con.prepareCall(SqlQuery.GET_DECISION_INFO_BY_PD_ID);
            cs.registerOutParameter("p_ls", OracleTypes.CURSOR);

            cs.setBigDecimal("p_pd_id", pdId);
            cs.execute();
            rs = (ResultSet) cs.getObject("p_ls");
            if (rs != null && rs.next()) {
                decisionInfo.setId(rs.getBigDecimal("d_id"));
                decisionInfo.setAmount(rs.getDouble("amount"));
                decisionInfo.setpSeries(rs.getString("p_series"));
                decisionInfo.setpNumber(rs.getString("p_number"));
                decisionInfo.setInvoiceCode(rs.getString("invoice_code"));
                decisionInfo.setIxmText(rs.getString("ixm_code_additional"));
                decisionInfo.setLawText(rs.getString("law_code_additional"));
            }
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getDecisionInfoByPdId", e), Constant.MTP_MD_APP_ID);
        } finally {
            JdbcUtil.close(rs, cs, con);
        }
        return decisionInfo;
    }

    @Override
    public ProtocolDetailInfo getProDetInfoById(BigDecimal id) {
        ProtocolDetailInfo protocolDetailInfo = new ProtocolDetailInfo();
        Connection con = null;
        CallableStatement cs = null;

        ResultSet rs = null;
        try {
            con = DatabaseHelper.connect();
            cs = con.prepareCall(SqlQuery.GET_PROTOCOL_DETAIL_INFO_BY_ID);
            cs.registerOutParameter("p_ls", OracleTypes.CURSOR);

            cs.setBigDecimal("p_pd_id", id);
            cs.execute();
            rs = (ResultSet) cs.getObject("p_ls");
            if (rs != null && rs.next()) {
                protocolDetailInfo.setId(id);
                protocolDetailInfo.setSeries(rs.getString("p_series"));
                protocolDetailInfo.setNumber(rs.getString("p_number"));
                protocolDetailInfo.setInvoiceCode(rs.getString("invoice_code"));
                protocolDetailInfo.setText1(rs.getString("text1"));
                protocolDetailInfo.setText2(rs.getString("text2"));
                protocolDetailInfo.setTakeDl(TypeUtil.intToBoolean(rs.getInt("take_dl")));
                protocolDetailInfo.setAmount(rs.getDouble("amount"));
                protocolDetailInfo.setStrId(rs.getBigDecimal("str_id"));
                protocolDetailInfo.setStrName(rs.getString("str_name"));
            }
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getProDetInfoById", e), Constant.MTP_MD_APP_ID);
        } finally {
            JdbcUtil.close(rs, cs, con);
        }
        return protocolDetailInfo;
    }

    @Override
    public List<UnPaidProInfo> getUnPaidProListByCarNumber(String carNumber) {
        List<UnPaidProInfo> unPaidProInfoList = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;

        ResultSet rs = null;
        try {
            con = DatabaseHelper.connect();
            cs = con.prepareCall(SqlQuery.GET_UNPAID_PROTOOL_INFO_lIST_BY_CAER_NUMBER);
            cs.registerOutParameter("p_ls", OracleTypes.CURSOR);

            cs.setString("p_veh_reg_number", carNumber);
            cs.execute();
            rs = (ResultSet) cs.getObject("p_ls");
            if (rs != null) {
                while (rs.next()) {

                    if (!unPaidProInfoList.isEmpty() && unPaidProInfoList.get(unPaidProInfoList.size() - 1).getId().compareTo(rs.getBigDecimal("pd_id")) == 0) {
                        unPaidProInfoList.get(unPaidProInfoList.size() - 1).getIxmDescriptionList().add(rs.getString("description"));
                    } else {
                        UnPaidProInfo unPaidProInfo = new UnPaidProInfo();
                        unPaidProInfo.setId(rs.getBigDecimal("pd_id"));
                        unPaidProInfo.setBall(rs.getInt("ball"));
                        unPaidProInfo.getIxmDescriptionList().add(rs.getString("description"));
                        unPaidProInfo.setSpeedMax(rs.getInt("sp_max"));
                        unPaidProInfo.setSpeedReal(rs.getInt("sp_real"));
                        unPaidProInfoList.add(unPaidProInfo);

                    }
                }
                logger.info(unPaidProInfoList);
            }
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getUnPaidProListByCarNumber", e), Constant.MTP_MD_APP_ID);;
        } finally {
            JdbcUtil.close(rs, cs, con);
        }
        return unPaidProInfoList;
    }

    @Override
    public List<Structure> getStrListByPdIdForRedirect(BigDecimal id) {
        List<Structure> structureList = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            con = DatabaseHelper.connect();
            cs = con.prepareCall(SqlQuery.STRUCTURE_LIST_BY_PD_ID_FOR_REDIRECT);
            cs.registerOutParameter("p_ls", OracleTypes.CURSOR);
            cs.setBigDecimal("p_pd_id", id);
            cs.execute();
            rs = (ResultSet) cs.getObject("p_ls");
            if (rs != null) {
                while (rs.next()) {
                    Structure structure = new Structure();
                    structure.setId(rs.getBigDecimal("str_id"));
                    structure.setName(rs.getString("str_name"));
                    structureList.add(structure);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getStrListByPdIdForRedirect", e), Constant.MTP_MD_APP_ID);
        } finally {
            JdbcUtil.close(rs, cs, con);
        }
        return structureList;
    }

    @Override
    public ProtocolDetail geProDetailById(BigDecimal id) {
        ProtocolDetail detail = new ProtocolDetail();
        Connection con = null;
        CallableStatement cs = null;

        ResultSet rs = null;
        try {
            con = DatabaseHelper.connect();
            cs = con.prepareCall(SqlQuery.GET_PROTOCOL_DETAIL_BY_ID);
            cs.registerOutParameter("p_ls", OracleTypes.CURSOR);
            cs.registerOutParameter("p_ixm_ls", OracleTypes.CURSOR);
            cs.registerOutParameter("p_law_ls", OracleTypes.CURSOR);
            cs.registerOutParameter("p_radar", OracleTypes.CURSOR);
            cs.registerOutParameter("p_announ_ls", OracleTypes.CURSOR);
            cs.setBigDecimal("p_pd_id", id);
            cs.execute();
            rs = (ResultSet) cs.getObject("p_ls");
            if (rs != null && rs.next()) {
                detail.setId(rs.getBigDecimal("pd_id"));
                detail.setCreateDate(rs.getTimestamp("pd_create_date"));
                detail.setInsertDate(rs.getTimestamp("pd_insert_date"));

                detail.setActionDate(rs.getTimestamp("pd_action_date"));
                detail.setActionLocation(rs.getString("pd_action_location"));
                detail.setProtocolType(ProtocolType.getValFrom(rs.getInt("pd_type_id")));

                detail.getProtocol().setSeries(rs.getString("p_series"));
                detail.getProtocol().setNumber(rs.getString("p_number"));

                detail.getStructure().setName(rs.getString("str_name"));

                detail.getVehicle().setCarNumber(rs.getString("v_reg_number"));
                detail.getVehicle().setMarka(rs.getString("v_marka"));

                detail.getDriverLicense().setSeries(rs.getString("dl_series"));
                detail.getDriverLicense().setNumber(rs.getString("dl_number"));
                detail.getDriverLicense().setCategories(rs.getString("dl_categories"));

                detail.getCitizen().setName(rs.getString("c_name"));
                detail.getCitizen().setSurname(rs.getString("c_surname"));
                detail.getCitizen().setPatronymic(rs.getString("c_patronymic"));
                detail.getCitizen().setBirthPlace(rs.getString("c_birth_place"));
                detail.getCitizen().setRegAddress(rs.getString("c_reg_address"));

                JdbcUtil.close(rs);
                rs = (ResultSet) cs.getObject("p_announ_ls");
                if (rs != null && rs.next()) {
                    Announcement announcement = new Announcement();
                    announcement.setDeliveryDate(rs.getTimestamp("delivery_date"));
                    announcement.setNote(rs.getString("note"));
                    announcement.getDriverLicense().setSeries(rs.getString("dl_series"));
                    announcement.getDriverLicense().setNumber(rs.getString("dl_number"));
                    detail.getAnnouncementList().add(announcement);
                }

                JdbcUtil.close(rs);
                rs = (ResultSet) cs.getObject("p_radar");
                if (rs != null && rs.next()) {
                    detail.getRadar().setId(rs.getBigDecimal("r_id"));
                    detail.getRadar().setStdNo(rs.getString("r_std_no"));
                    detail.getRadar().setCarNumber(rs.getString("r_car_number"));
                    detail.getRadar().setCarMarka(rs.getString("r_car_marka"));
                    detail.getRadar().setActionDate(rs.getTimestamp("r_action_date"));
                    detail.getRadar().setActionLocation(rs.getString("r_action_location"));
                    detail.getRadar().getStdCaptureType().setId(rs.getBigDecimal("r_std_capture_type_id"));
                    detail.getRadar().setSpeedReal(rs.getInt("r_sp_real"));
                    detail.getRadar().setSpeedMax(rs.getInt("r_sp_max"));
                    detail.getRadar().setSpeedOver(rs.getInt("r_sp_over"));
                    detail.getRadar().setVideoId(rs.getBigDecimal("r_video_id"));
                    detail.getRadar().getStd().setMarka(rs.getString("std_marka"));
                    detail.getRadar().getStd().setModel(rs.getString("std_model"));
                    detail.getRadar().getStd().setLastRegDate(rs.getTimestamp("std_last_regdate"));
                    detail.getRadar().getStd().setNextRegDate(rs.getTimestamp("std_next_regdate"));
                    detail.getRadar().getStd().setLastCode(rs.getString("std_last_code"));
                    detail.getRadar().getStoppingPlace().setId(rs.getBigDecimal("sp_id"));
                    detail.getRadar().getStoppingPlace().setName(rs.getString("sp_name"));
                    detail.getRadar().getStoppingPlace().setPlace(rs.getString("sp_place"));
                }

                JdbcUtil.close(rs);
                rs = (ResultSet) cs.getObject("p_ixm_ls");
                if (rs != null) {
                    while (rs.next()) {
                        Ixm ixm = new Ixm();
                        ixm.setDescription(rs.getString("description"));
                        ixm.setCodeAdditional(rs.getString("code_additional"));
                        ixm.setAmount(rs.getDouble("amount"));
                        ixm.setMmxName(rs.getString("mmx_name"));
                        detail.getIxmMap().put(ixm.getId(), ixm);
                    }
                }

                JdbcUtil.close(rs);
                rs = (ResultSet) cs.getObject("p_law_ls");
                if (rs != null) {
                    while (rs.next()) {
                        Law law = new Law();
                        law.setDescription(rs.getString("description"));
                        law.setCodeAdditional(rs.getString("code_additional"));
                        detail.getLawMap().put(law.getId(), law);
                    }
                }
            }

        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("geProDetailById", e), Constant.MTP_MD_APP_ID);
        } finally {
            JdbcUtil.close(rs, cs, con);
        }
        return detail;
    }

    @Override
    public List<Menu> getMenuListByDepId(BigDecimal depId) {
        List<Menu> menuList = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            con = DBHelper.connect();
            logger.info(con);
            cs = con.prepareCall(" ? =call dbo.dbo.SELECT_SCREENMENU(1)");
            cs.getResultSet();
//            cs.setBigDecimal("p_pd_id",id);                                       
//            cs.execute();  
//            rs = (ResultSet) cs.getObject(1);
            if(rs != null) {                
                while (rs.next()) { 
                    logger.info(rs.getBigDecimal(1));
//                    Structure structure   = new Structure();
//                    structure.setId(rs.getBigDecimal("str_id"));
//                    structure.setName(rs.getString("str_name"));
//                    structureList.add(structure);
                }       
            }
        
            if (con != null) {
                logger.info("not null");
                Menu m = new Menu();
                m.setName("pizzani yedin");
                menuList.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
//            LogHelper.insertError(new ErrorModel("getStrListByPdIdForRedirect", e),Constant.MTP_MD_APP_ID);            
        } finally {
            DBUtil.close(rs, cs, con);
        }
        return menuList;
    }
    
    public static void main(String[] args) {
        new RestWsDaoImpl().getMenuListByDepId(BigDecimal.ZERO);
    }
}
