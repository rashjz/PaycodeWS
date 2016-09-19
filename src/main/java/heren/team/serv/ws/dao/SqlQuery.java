package heren.team.serv.ws.dao;

/**
 *
 * @author Vusal Hasanli
 */
public class SqlQuery {
    public static final String USER_LIST_BY_STR_ID = "{call :p_ls:=  protocol_main.pkg_md_rs.ls_user_by_str_id(:p_str_id)}";
    public static final String USER_LOGIN = "{call protocol_main.pkg_md_rs.login_user_md(:p_id,:p_username,:p_password,:p_device_id,:p_gpsx,:p_gpsy,:p_gpsz,:p_loginid,:p_user)}";
    public static final String USER_PASSWORD_UPDATE = "{call protocol_main.pkg_user.change_password(:p_user_id ,:p_old_password ,:p_password ,:p_loginid ,:p_userid ,:p_result )}";
    public static final String GET_NEW_RADAR_PROTOCOL_DETAIL_lIST_BY_CAER_NUMBER = "{call :p_ls:=protocol_main.pkg_md_rs.ls_new_radar_pro_by_carnum(:p_veh_reg_number)}";
    public static final String GET_BALL_PROTOOL_DETAIL_lIST_BY_CAER_NUMBER = "{call :p_ls:=  protocol_main.pkg_md_rs.ls_ball_pro_by_carnum(:p_veh_reg_number)}";    
    public static final String GET_DECISION_INFO_BY_PD_ID  = "{call :p_ls:= protocol_main.pkg_md_rs.decis_info_by_pd_id(:p_pd_id)}";
    public static final String GET_PROTOCOL_DETAIL_INFO_BY_ID  = "{call :p_ls:=  protocol_main.pkg_md_rs.pro_detail_info_by_id(:p_pd_id)}";
    public static final String GET_UNPAID_PROTOOL_INFO_lIST_BY_CAER_NUMBER = "{call :p_ls:=protocol_main.pkg_md_rs.ls_unpaid_pro_by_carnum(:p_veh_reg_number)}";        
    public static final String STRUCTURE_LIST_BY_PD_ID_FOR_REDIRECT = "{call :p_ls:=protocol_main.pkg_md_rs.ls_str_by_pd_id_for_red(:p_pd_id)}";        
    public static final String GET_PROTOCOL_DETAIL_BY_ID  = "{call protocol_main.pkg_md_rs.pro_detail_by_id_md(:p_ls,:p_ixm_ls,:p_law_ls,:p_radar,:p_announ_ls,:p_pd_id)}";
    
}
