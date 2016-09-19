package heren.team.serv.ws.service;

import az.gov.mia.mtp.domain.ProtocolDetail;
import az.gov.mia.mtp.domain.hr.Structure;
import az.gov.mia.mtp.domain.security.User;
import heren.team.serv.ws.dao.RestWsDao;
import heren.team.serv.ws.dao.RestWsDaoImpl;
import heren.team.serv.ws.domain.BallInfo;
import heren.team.serv.ws.domain.DecisionInfo;
import heren.team.serv.ws.domain.Menu;
import heren.team.serv.ws.domain.ProtocolDetailInfo;
import heren.team.serv.ws.domain.UnPaidProInfo;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Vusal Hasanli
 */
public class RestWsServiceImpl implements RestWsService{
    private final RestWsDao dao = new RestWsDaoImpl();
    
    @Override
    public List<User> getUserListByStrId(BigDecimal strId) {
        return dao.getUserListByStrId(strId);
    }

    @Override
    public User loginUser(User user) {
        return dao.loginUser(user);
    }

    @Override
    public List<ProtocolDetail> getNewRadarProListByCarNumber(String carNumber) {
        return dao.getNewRadarProListByCarNumber(carNumber);
    }

    @Override
    public List<BallInfo> getBallInfoListByCarNumber(String carNumber) {
        return dao.getBallInfoListByCarNumber(carNumber);
    }

    @Override
    public DecisionInfo getDecisionInfoByPdId(BigDecimal pdId) {
        return dao.getDecisionInfoByPdId(pdId);
    }

    @Override
    public ProtocolDetailInfo getProDetInfoById(BigDecimal id) {
        return dao.getProDetInfoById(id);
    }

    @Override
    public List<UnPaidProInfo> getUnPaidProListByCarNumber(String carNumber) {
        return dao.getUnPaidProListByCarNumber(carNumber);
    }

    @Override
    public List<Structure> getStrListByPdIdForRedirect(BigDecimal id) {
        return dao.getStrListByPdIdForRedirect(id);
    }

    @Override
    public ProtocolDetail geProDetailById(BigDecimal id) {
        return dao.geProDetailById(id);
    }
    
    
   @Override
    public List<Menu> getMenuListByDepId(BigDecimal depId) {
        return dao.getMenuListByDepId(depId);
    }
}
