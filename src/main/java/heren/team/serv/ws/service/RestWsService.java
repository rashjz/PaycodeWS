package heren.team.serv.ws.service;

import az.gov.mia.mtp.domain.ProtocolDetail;
import az.gov.mia.mtp.domain.hr.Structure;
import az.gov.mia.mtp.domain.security.User;
import heren.team.serv.ws.domain.BallInfo;
import heren.team.serv.ws.domain.DecisionInfo;
import heren.team.serv.ws.domain.Menu;
import heren.team.serv.ws.domain.ProtocolDetailInfo;
import heren.team.serv.ws.domain.UnPaidProInfo;
import java.math.BigDecimal;
import java.util.List;
import javax.ws.rs.PathParam;

/**
 *
 * @author Vusal Hasanli
 */
public interface RestWsService {
    List<User> getUserListByStrId(BigDecimal strId);
    List<ProtocolDetail> getNewRadarProListByCarNumber(String carNumber);
    List<BallInfo> getBallInfoListByCarNumber(String carNumber);
    DecisionInfo getDecisionInfoByPdId(BigDecimal pdId);    
    ProtocolDetailInfo getProDetInfoById(BigDecimal id);
    List<UnPaidProInfo> getUnPaidProListByCarNumber(String carNumber);
    List<Structure> getStrListByPdIdForRedirect(BigDecimal id);
    ProtocolDetail geProDetailById(@PathParam("id")BigDecimal id);
    
    User loginUser(User user);
    
    
    List<Menu> getMenuListByDepId(BigDecimal depId);
}
