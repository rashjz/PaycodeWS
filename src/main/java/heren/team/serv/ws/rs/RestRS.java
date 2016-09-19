package heren.team.serv.ws.rs;

import az.gov.mia.mtp.domain.Constant;
import az.gov.mia.mtp.domain.DriverLicense;
import az.gov.mia.mtp.domain.ErrorModel;
import az.gov.mia.mtp.domain.Ftp;
import az.gov.mia.mtp.domain.Gender;
import az.gov.mia.mtp.domain.Ixm;
import az.gov.mia.mtp.domain.Law;
import az.gov.mia.mtp.domain.MtpAppException;
import az.gov.mia.mtp.domain.PersonStatus;
import az.gov.mia.mtp.domain.ProtocolDetail;
import az.gov.mia.mtp.domain.ProtocolType;
import az.gov.mia.mtp.domain.Proxy;
import az.gov.mia.mtp.domain.Vehicle;
import az.gov.mia.mtp.domain.radar.StoppingPlace;
import az.gov.mia.mtp.domain.hr.Employee;
import az.gov.mia.mtp.domain.hr.Structure;
import az.gov.mia.mtp.domain.security.User;
import az.gov.mia.mtp.helper.LogHelper;
import heren.team.serv.ws.domain.AnnouncementWrapper;
import heren.team.serv.ws.domain.BallInfo;
import heren.team.serv.ws.domain.ConstantMd;
import heren.team.serv.ws.domain.CpfrResult;
import heren.team.serv.ws.domain.CpfrWrapper;
import heren.team.serv.ws.domain.DecisionInfo;
import heren.team.serv.ws.domain.DecisionWrapper;
import heren.team.serv.ws.domain.ProtocolDetailInfo;
import heren.team.serv.ws.domain.ProtocolDetailWrapper;
import heren.team.serv.ws.domain.RedirectWrapper;
import heren.team.serv.ws.domain.UnPaidProInfo;
import heren.team.serv.ws.service.RestWsService;
import heren.team.serv.ws.service.RestWsServiceImpl;
import az.gov.mia.mtp.remote.dao.service.DriverLicenseService;
import az.gov.mia.mtp.remote.dao.service.ProxyService;
import az.gov.mia.mtp.remote.dao.service.RemoteServiceFactory;
import az.gov.mia.mtp.remote.dao.service.RemoteServiceName;
import az.gov.mia.mtp.remote.dao.service.VehicleService;
import az.gov.mia.mtp.service.AnnouncementService;
import az.gov.mia.mtp.service.DecisionService;
import az.gov.mia.mtp.service.EmployeeService;
import az.gov.mia.mtp.service.FtpService;
import az.gov.mia.mtp.service.IxmService;
import az.gov.mia.mtp.service.LawService;
import az.gov.mia.mtp.service.ProtocolDetailService;
import az.gov.mia.mtp.service.RadarService;
import az.gov.mia.mtp.service.RedirectService;
import az.gov.mia.mtp.service.StructureService;
import az.gov.mia.mtp.service.UserService;
import az.gov.mia.mtp.service.factory.ServiceFactory;
import az.gov.mia.mtp.service.factory.ServiceName;
import az.gov.mia.mtp.util.DateUtil;
import az.gov.mia.mtp.util.FtpRadarUtil;
import az.gov.mia.mtp.util.TypeUtil;
import heren.team.serv.ws.domain.Menu;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;

/**
 * REST Web Service
 *
 * @author Vusal Hasanli
 */
@Path("RestRS")
public class RestRS {

    private static final Logger logger = Logger.getLogger(RestRS.class);
    private static RestWsService service = null;
//    private static User user = null;
    @Context
    private UriInfo context;

    static {
        logger.info("static");
        try {
            ServiceFactory.createServices();
            service = new RestWsServiceImpl();
//          
//            FtpService ftpService = (FtpService) ServiceFactory.get(ServiceName.FTP_SERVICE);
//            Ftp ftp = ftpService.getFtpInfo();
//            if (ftp == null) {
//                throw new Error("Ftp Info is null !");
//            }
//            FtpRadarUtil.setFtp(ftp);
//            user =new User();
//            user.setId(new BigDecimal(127));
//            user.getEmployee().setEmpPositionId(new BigDecimal(10648));
//            user.getEmployee().getStructure().setId(new BigDecimal(200));
//            user.getParam().setAppId(2);
//            user.getParam().setLogInId(new BigDecimal(-1));                        

        } catch (Error e) {
            logger.error(e);
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            throw new Error(e.getCause());
        }
    }

    public RestRS() {
    }

    @GET
    @Path("/getDate")
    @Produces(MediaType.TEXT_PLAIN)
    public String getDate() throws MtpAppException {
        try {
            return DateUtil.dateToStr(new Date(), DateUtil.FMT_DATE_P);
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getDate", e), Constant.MTP_MD_APP_ID);
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        }
    }

    @GET
    @Path("/getTime")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTime() throws MtpAppException {
        try {
            return DateUtil.dateToStr(new Date(), DateUtil.FMT_TIME_C);
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getTime", e), Constant.MTP_MD_APP_ID);
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        }
    }

    @GET
    @Path("/getStructureList")
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public List<Structure> getStructureList() {
        List<Structure> list = null;
        try {
            StructureService structureService = (StructureService) ServiceFactory.get(ServiceName.STRUCTURE_SERVICE);
            list = structureService.getStructureList(1, false);
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getStructureList", e), Constant.MTP_MD_APP_ID);
        }
        return (list != null) ? list : new ArrayList();
    }

    @GET
    @Path("/getEmployeeListByStrId/{strId}")
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public List<Employee> getEmployeeListByStrId(@PathParam("strId") BigDecimal strId) throws MtpAppException {
        List<Employee> list = null;
        try {
            if (strId == null) {
                throw new MtpAppException(ConstantMd.MSG_STR_ID_NOT_NULL);
            }
            EmployeeService employeeService = (EmployeeService) ServiceFactory.get(ServiceName.EMPLOYEE_SERVICE);
            list = employeeService.getEmployeeListByStrIdAndDate(strId, new Date());
        } catch (MtpAppException e) {
            logger.error(e);
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getEmployeeListByStrId", e), Constant.MTP_MD_APP_ID);
        }
        return (list != null) ? list : new ArrayList();
    }

    @GET
    @Path("/getEmployeeByEmpPosId/{empPosId}")
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public Employee getEmployeeByEmpPosId(@PathParam("empPosId") BigDecimal empPosId) throws MtpAppException {
        Employee employee = null;
        try {
            if (empPosId == null) {
                throw new MtpAppException(ConstantMd.MSG_EMPPOS_ID_NOT_NULL);
            }
            EmployeeService employeeService = (EmployeeService) ServiceFactory.get(ServiceName.EMPLOYEE_SERVICE);
            employee = employeeService.getEmployeeByEmpPosId(empPosId);
        } catch (MtpAppException e) {
            logger.error(e);
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getEmployeeByEmpPosId", e), Constant.MTP_MD_APP_ID);
        }
        return (employee != null) ? employee : new Employee();
    }

    @GET
    @Path("/getPersonStatusList")
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public List<PersonStatus> getPersonStatusList() {
        List<PersonStatus> list = null;
        try {
            ProtocolDetailService detailService = (ProtocolDetailService) ServiceFactory.get(ServiceName.PROTOCOL_SERVICE);
            list = detailService.getPersonStatusList();
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getPersonStatusList", e), Constant.MTP_MD_APP_ID);
        }
        return (list != null) ? list : new ArrayList();
    }

    @GET
    @Path("/getIxmList")
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public List<Ixm> getIxmList() {
        List<Ixm> list = null;
        try {
            IxmService ixmService = (IxmService) ServiceFactory.get(ServiceName.IXM_SERVICE);
            list = ixmService.getIxmListByDate(new Date());
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getIxmList", e), Constant.MTP_MD_APP_ID);
        }
        return (list != null) ? list : new ArrayList();
    }

    @GET
    @Path("/getLawList")
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public List<Law> getLawList() {
        List<Law> list = null;
        try {
            LawService lawService = (LawService) ServiceFactory.get(ServiceName.LAW_SERVICE);
            list = lawService.getLawListByDate(new Date());
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getLawList", e), Constant.MTP_MD_APP_ID);
        }
        return (list != null) ? list : new ArrayList();
    }

    @GET
    @Path("/getStoppingPlaceList")
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public List<StoppingPlace> getStoppingPlaceList() {
        List<StoppingPlace> list = null;
        try {
            RadarService radarService = (RadarService) ServiceFactory.get(ServiceName.RADAR_SERVICE);
            list = radarService.getStoppingPlaceList();
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getStoppingPlaceList", e), Constant.MTP_MD_APP_ID);
        }
        return (list != null) ? list : new ArrayList();
    }

    @GET
    @Path("/getUserListByStrId/{strId}")
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public List<User> getUserListByStrId(@PathParam("strId") BigDecimal strId) throws MtpAppException {
        List<User> list = null;
        try {
            if (strId == null) {
                throw new MtpAppException(ConstantMd.MSG_STR_NOT_NULL);
            }
            list = service.getUserListByStrId(strId);
        } catch (MtpAppException e) {
            logger.error(e);
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getUserListByStrId", e), Constant.MTP_MD_APP_ID);

        }
        return (list != null) ? list : new ArrayList();
    }

    @GET
    @Path("/getNewRadarProListByCarNumber/{carNumber}")
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public List<ProtocolDetail> getNewRadarProDetailListByCarNumber(@PathParam("carNumber") String carNumber) throws MtpAppException {
        List<ProtocolDetail> list = null;
        try {
            if (carNumber == null || carNumber.trim().isEmpty()) {
                throw new MtpAppException(ConstantMd.MSG_CAR_NUMBER_NOT_NULL);
            }
            if (carNumber.trim().length() <= 3) {
                throw new MtpAppException(ConstantMd.MSG_CAR_NUMBER_LENGTH_LIMIT_NOT_CORRECT);
            }
            list = service.getNewRadarProListByCarNumber(carNumber);
        } catch (MtpAppException e) {
            logger.error(e);
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getNewRadarProDetailListByCarNumber", e), Constant.MTP_MD_APP_ID);
        }
        return (list != null) ? list : new ArrayList();
    }

    @GET
    @Path("/getBallInfolListByCarNumber/{carNumber}")
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public List<BallInfo> getBallInfoListByCarNumber(@PathParam("carNumber") String carNumber) throws MtpAppException {
        List<BallInfo> list = null;
        try {
            if (carNumber == null || carNumber.trim().isEmpty()) {
                throw new MtpAppException(ConstantMd.MSG_CAR_NUMBER_NOT_NULL);
            }
            if (carNumber.trim().length() <= 3) {
                throw new MtpAppException(ConstantMd.MSG_CAR_NUMBER_LENGTH_LIMIT_NOT_CORRECT);
            }
            list = service.getBallInfoListByCarNumber(carNumber);
        } catch (MtpAppException e) {
            logger.error(e);
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getBallInfoListByCarNumber", e), Constant.MTP_MD_APP_ID);
        }
        return (list != null) ? list : new ArrayList();
    }

    @GET
    @Path("/getDecisionInfoByPdId/{proDetId}")
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public DecisionInfo getDecisionInfoByPdId(@PathParam("proDetId") BigDecimal proDetId) throws MtpAppException {
        DecisionInfo decisionInfo = null;
        try {
            if (proDetId == null) {
                throw new MtpAppException(ConstantMd.MSG_PRO_DET_ID_NOT_NULL);
            }
            decisionInfo = service.getDecisionInfoByPdId(proDetId);
        } catch (MtpAppException e) {
            logger.error(e);
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getDecisionInfoByPdId", e), Constant.MTP_MD_APP_ID);
        }
        return (decisionInfo != null) ? decisionInfo : new DecisionInfo();
    }

    @GET
    @Path("/getProDetInfoById/{id}")
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public ProtocolDetailInfo getProDetInfoById(@PathParam("id") BigDecimal id) throws MtpAppException {
        ProtocolDetailInfo protocolDetailInfo = null;
        try {
            if (id == null) {
                throw new MtpAppException(ConstantMd.MSG_PRO_DET_ID_NOT_NULL);
            }
            protocolDetailInfo = service.getProDetInfoById(id);
        } catch (MtpAppException e) {
            logger.error(e);
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getProDetInfoById", e), Constant.MTP_MD_APP_ID);
        }
        return (protocolDetailInfo != null) ? protocolDetailInfo : new ProtocolDetailInfo();
    }

    @GET
    @Path("/getUnPaidProListByCarNumber/{carNumber}")
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public List<UnPaidProInfo> getUnPaidProListByCarNumber(@PathParam("carNumber") String carNumber) throws MtpAppException {
        List<UnPaidProInfo> list = null;
        try {
            if (carNumber == null || carNumber.trim().isEmpty()) {
                throw new MtpAppException(ConstantMd.MSG_CAR_NUMBER_NOT_NULL);
            }
            if (carNumber.trim().length() <= 3) {
                throw new MtpAppException(ConstantMd.MSG_CAR_NUMBER_LENGTH_LIMIT_NOT_CORRECT);
            }
            list = service.getUnPaidProListByCarNumber(carNumber);
        } catch (MtpAppException e) {
            logger.error(e);
            throw e;
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getUnPaidProListByCarNumber", e), Constant.MTP_MD_APP_ID);
        }
        return (list != null) ? list : new ArrayList();
    }

    @GET
    @Path("/geProDetailById")
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public ProtocolDetail geProDetailById(@QueryParam("id") BigDecimal id, @QueryParam("image") boolean image) throws MtpAppException {
        ProtocolDetail detail = null;
        try {
            if (id == null) {
                throw new MtpAppException(ConstantMd.MSG_PRO_DET_ID_NOT_NULL);
            }
            detail = service.geProDetailById(id);
            if (image && detail != null && detail.getProtocolType() == ProtocolType.Raddar) {
                RadarService radarService = (RadarService) ServiceFactory.get(ServiceName.RADAR_SERVICE);
                radarService.getRadarImageList(detail.getRadar());
                FtpRadarUtil.getRadarImageList(detail.getRadar().getImageList());
            }
        } catch (MtpAppException e) {
            logger.error(e);
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("geProDetailById", e), Constant.MTP_MD_APP_ID);
        }
        return (detail != null) ? detail : new ProtocolDetail();
    }

    @GET
    @Path("/getStrListByPdIdForRedirect/{id}")
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public List<Structure> getStrListByPdIdForRedirect(@PathParam("id") BigDecimal id) throws MtpAppException {
        List<Structure> list = null;
        System.out.println("Salam");
        try {
            if (id == null) {
                throw new MtpAppException(ConstantMd.MSG_PRO_DET_ID_NOT_NULL);
            }
            list = service.getStrListByPdIdForRedirect(id);
            if (list == null) {
                list = new ArrayList<>();
            }
        } catch (MtpAppException e) {
            logger.error(e);
            throw e;
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getStrListByPdIdForRedirect", e), Constant.MTP_MD_APP_ID);
        }
        return (list != null) ? list : new ArrayList();
    }

    @GET
    @Path("/getDlBySerAndNum/{series}/{number}")
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)

    public DriverLicense getDLBySerAndNum(@PathParam("series") String series, @PathParam("number") String number) throws MtpAppException {
        DriverLicense dl = null;
        try {
            if (!TypeUtil.strToBoolean(series)) {
                throw new MtpAppException(ConstantMd.MSG_DL_SERIES_NOT_NULL);
            }
            if (!TypeUtil.strToBoolean(number)) {
                throw new MtpAppException(ConstantMd.MSG_DL_NUMBER_NOT_NULL);
            }
            DriverLicenseService driverLicenseService = (DriverLicenseService) RemoteServiceFactory.get(RemoteServiceName.DL_SERVICE);
            dl = driverLicenseService.getDLBySerAndNum(series, number);
        } catch (MtpAppException e) {
            logger.error(e);
            throw e;
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getDLBySerAndNum", e), Constant.MTP_MD_APP_ID);
        }
        return (dl != null) ? dl : new DriverLicense();
    }

    @GET
    @Path("/getDLListByPersonWithLike/{name}/{surname}/{patronymic}")
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public List<DriverLicense> getDLListByPersonWithLike(@PathParam("name") String name, @PathParam("surname") String surname, @PathParam("patronymic") String patronymic) throws MtpAppException {
        List<DriverLicense> list = null;
        try {
            if (!TypeUtil.strToBoolean(name)) {
                throw new MtpAppException(ConstantMd.MSG_CITIZEN_NAME_NOT_NULL);
            }
            if (!TypeUtil.strToBoolean(surname)) {
                throw new MtpAppException(ConstantMd.MSG_CITIZEN_SURNAME_NOT_NULL);
            }
            if (!TypeUtil.strToBoolean(patronymic)) {
                throw new MtpAppException(ConstantMd.MSG_CITIZEN_PATRONYMIC_NOT_NULL);
            }
            DriverLicenseService driverLicenseService = (DriverLicenseService) RemoteServiceFactory.get(RemoteServiceName.DL_SERVICE);
            list = driverLicenseService.getDLListByPersonWithLike(name, surname, patronymic);
        } catch (MtpAppException e) {
            logger.error(e);
            throw e;
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getDLByPersonWithLike", e), Constant.MTP_MD_APP_ID);
        }
        return (list != null) ? list : new ArrayList();
    }

    @GET
    @Path("/getVehicleByCarNumber/{carNumber}")
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public Vehicle getVehicleByCarNumber(@PathParam("carNumber") String carNumber) throws MtpAppException {
        Vehicle vehicle = null;
        try {
            if (!TypeUtil.strToBoolean(carNumber)) {
                throw new MtpAppException(ConstantMd.MSG_CAR_NUMBER_NOT_NULL);
            }
            VehicleService vehicleService = (VehicleService) RemoteServiceFactory.get(RemoteServiceName.VEHICLE_SERVICE);
            vehicle = vehicleService.getVehicleByVehRegNumber(carNumber);
        } catch (MtpAppException e) {
            logger.error(e);
            throw e;
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getVehicleByCarNumber", e), Constant.MTP_MD_APP_ID);
        }
        return (vehicle != null) ? vehicle : new Vehicle();
    }

    @GET
    @Path("/getProxyListByCarNumber/{carNumber}")
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public List<Proxy> getProxyListByCarNumber(@PathParam("carNumber") String carNumber) throws MtpAppException {
        List<Proxy> list = null;
        try {
            if (!TypeUtil.strToBoolean(carNumber)) {
                throw new MtpAppException(ConstantMd.MSG_CAR_NUMBER_NOT_NULL);
            }
            ProxyService proxyService = (ProxyService) RemoteServiceFactory.get(RemoteServiceName.PROXY_SERVICE);
            list = proxyService.getProxyListByCarNumber(carNumber);
        } catch (MtpAppException e) {
            logger.error(e);
            throw e;
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("getProxyListByCarNumber", e), Constant.MTP_MD_APP_ID);
        }
        return (list != null) ? list : new ArrayList();
    }

    @POST
    @Path("/insertAnnouncement")
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public void insertAnnouncement(AnnouncementWrapper wrapper) throws MtpAppException {
        try {
            if (wrapper.getAnnouncement() == null) {
                throw new MtpAppException(ConstantMd.MSG_ANNNOCMENT_INFO_NOT_NULL);
            } else if (wrapper.getUser() == null) {
                throw new MtpAppException(ConstantMd.MSG_USER_INFO_NOT_NULL);
            } else if (wrapper.getUser().getEmployee().getStructure() == null || !TypeUtil.bigDecimalToBoolean(wrapper.getUser().getEmployee().getStructure().getId())) {
                throw new MtpAppException(ConstantMd.MSG_USER_STR_NOT_NULL);
            } else if (!TypeUtil.bigDecimalToBoolean(wrapper.getAnnouncement().getProDetailId())) {
                throw new MtpAppException(ConstantMd.MSG_PRO_DET_ID_NOT_NULL);
            } else if (!TypeUtil.strToBoolean(wrapper.getAnnouncement().getDriverLicense().getSeries())) {
                throw new MtpAppException(ConstantMd.MSG_DL_SERIES_NOT_NULL);
            } else if (!TypeUtil.strToBoolean(wrapper.getAnnouncement().getDriverLicense().getNumber())) {
                throw new MtpAppException(ConstantMd.MSG_DL_NUMBER_NOT_NULL);
            }

            AnnouncementService announcementService = (AnnouncementService) ServiceFactory.get(ServiceName.ANNOUNCEMENT_SERVICE);
            String result = announcementService.insertAnnouncement(wrapper.getAnnouncement(), wrapper.getUser());
            if (!result.isEmpty()) {
                throw new MtpAppException(result);
            }
        } catch (MtpAppException e) {
            logger.error(e);
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("insertAnnouncement", e, wrapper.toString()), wrapper.getUser());
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        }
    }

    @POST
    @Path("/insertRedirect")
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public void insertRedirect(RedirectWrapper wrapper) throws MtpAppException {
        try {
            if (wrapper.getRedirect() == null) {
                throw new MtpAppException(ConstantMd.MSG_REDIRECT_INFO_NOT_NULL);
            } else if (wrapper.getUser() == null) {
                throw new MtpAppException(ConstantMd.MSG_USER_INFO_NOT_NULL);
            } else if (wrapper.getUser().getEmployee().getStructure() == null || !TypeUtil.bigDecimalToBoolean(wrapper.getUser().getEmployee().getStructure().getId())) {
                throw new MtpAppException(ConstantMd.MSG_USER_STR_NOT_NULL);
            } else if (wrapper.getUser().getEmployee() == null || !TypeUtil.bigDecimalToBoolean(wrapper.getUser().getEmployee().getEmpPositionId())) {
                throw new MtpAppException(ConstantMd.MSG_USER_EMPPOS_ID_NOT_NULL);
            } else if (!TypeUtil.bigDecimalToBoolean(wrapper.getRedirect().getProDetailId())) {
                throw new MtpAppException(ConstantMd.MSG_PRO_DET_ID_NOT_NULL);
            } else if (wrapper.getRedirect().getToStructure() == null || !TypeUtil.bigDecimalToBoolean(wrapper.getRedirect().getToStructure().getId())) {
                throw new MtpAppException(ConstantMd.MSG_REDIRECT_TO_STR_NOT_NULL);
            }
            RedirectService redirectService = (RedirectService) ServiceFactory.get(ServiceName.REDIRECT_SERVICE);
            String result = redirectService.insertRedirect(wrapper.getRedirect(), wrapper.getUser());
            if (!result.isEmpty()) {
                throw new MtpAppException(result);
            }
        } catch (MtpAppException e) {
            logger.error(e);
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("insertRedirect", e, wrapper.toString()), wrapper.getUser());
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        }
    }

    @POST
    @Path("/createProtocolFromRadar/{radarId}")
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public CpfrResult createProtocolFromRadar(CpfrWrapper wrapper) throws MtpAppException {
        CpfrResult cpfrResult = new CpfrResult();
        cpfrResult.setNote("Hazir deyil");
        String result = "";
        try {
            if (!TypeUtil.bigDecimalToBoolean(wrapper.getRadarId())) {
                throw new MtpAppException(ConstantMd.MSG_RADAR_ID_NOT_NULL);
            }
//            RedirectService redirectService =(RedirectService) ServiceFactory.get(ServiceName.REDIRECT_SERVICE);
//            result = redirectService.insertRedirect(wrapper.getRedirect(),wrapper.getUser());
//            if(!result.isEmpty()) {
//                throw new MtpAppException(result);
//            }              
        } catch (MtpAppException e) {
            logger.error(e);
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("createProtocolFromRadar", e, wrapper.toString()), wrapper.getUser());
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        }
        return cpfrResult;
    }

    @POST
    @Path("/insertDecision")
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public void insertDecision(DecisionWrapper wrapper) throws MtpAppException {
        try {
            if (wrapper.getDecision() == null) {
                throw new MtpAppException(ConstantMd.MSG_DECISION_INFO_NOT_NULL);
            } else if (wrapper.getUser() == null) {
                throw new MtpAppException(ConstantMd.MSG_USER_INFO_NOT_NULL);
            } else if (wrapper.getUser().getEmployee().getStructure() == null || !TypeUtil.bigDecimalToBoolean(wrapper.getUser().getEmployee().getStructure().getId())) {
                throw new MtpAppException(ConstantMd.MSG_USER_STR_NOT_NULL);
            } else if (!TypeUtil.bigDecimalToBoolean(wrapper.getUser().getEmployee().getEmpPositionId())) {
                throw new MtpAppException(ConstantMd.MSG_USER_EMPPOS_ID_NOT_NULL);
            } else if (!TypeUtil.bigDecimalToBoolean(wrapper.getDecision().getProDetailId())) {
                throw new MtpAppException(ConstantMd.MSG_PRO_DET_ID_NOT_NULL);
            }
            DecisionService decisionService = (DecisionService) ServiceFactory.get(ServiceName.DECISION_SERVICE);

            String result = decisionService.insertDecision(wrapper.getDecision(), wrapper.getUser());
            if (!result.isEmpty()) {
                throw new MtpAppException(result);
            }
        } catch (MtpAppException e) {
            logger.error(e);
            throw e;
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("insertDecision", e, wrapper.toString()), wrapper.getUser());
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        }
    }

    @POST
    @Path("/insertProtocolDetail")
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public BigDecimal insertProtocolDetail(ProtocolDetailWrapper wrapper) throws MtpAppException {
        BigDecimal id = null;
        try {
            if (wrapper.getDetail() == null) {
                throw new MtpAppException(ConstantMd.MSG_DECISION_INFO_NOT_NULL);
            } else if (wrapper.getUser() == null) {
                throw new MtpAppException(ConstantMd.MSG_USER_INFO_NOT_NULL);
            } else if (wrapper.getUser().getEmployee().getStructure() == null || !TypeUtil.bigDecimalToBoolean(wrapper.getUser().getEmployee().getStructure().getId())) {
                throw new MtpAppException(ConstantMd.MSG_USER_STR_NOT_NULL);
            } else if (!TypeUtil.bigDecimalToBoolean(wrapper.getUser().getEmployee().getEmpPositionId())) {
                throw new MtpAppException(ConstantMd.MSG_USER_EMPPOS_ID_NOT_NULL);
            } else if (wrapper.getDetail().getCreateDate() == null) {
                throw new MtpAppException(ConstantMd.MSG_CREATE_DATE_NOT_NULL);
            } else if (wrapper.getDetail().getActionDate() == null) {
                throw new MtpAppException(ConstantMd.MSG_ACTION_DATE_NOT_NULL);
            } else if (!TypeUtil.strToBoolean(wrapper.getDetail().getActionLocation())) {
                throw new MtpAppException(ConstantMd.MSG_ACTION_LOCATION_NOT_NULL);
            } else if (!TypeUtil.strToBoolean(wrapper.getDetail().getDriverLicense().getSeries())) {
                throw new MtpAppException(ConstantMd.MSG_DL_SERIES_NOT_NULL);
            } else if (!TypeUtil.strToBoolean(wrapper.getDetail().getDriverLicense().getNumber())) {
                throw new MtpAppException(ConstantMd.MSG_DL_NUMBER_NOT_NULL);
            } else if (!TypeUtil.strToBoolean(wrapper.getDetail().getDriverLicense().getCategories())) {
                throw new MtpAppException(ConstantMd.MSG_DL_CATEGORY_NOT_NULL);
            } else if (!TypeUtil.strToBoolean(wrapper.getDetail().getVehicle().getCarNumber())) {
                throw new MtpAppException(ConstantMd.MSG_CAR_NUMBER_NOT_NULL);
            } else if (!TypeUtil.strToBoolean(wrapper.getDetail().getVehicle().getMarka())) {
                throw new MtpAppException(ConstantMd.MSG_CAR_MARKA_NOT_NULL);
            } else if (wrapper.getDetail().getPersonStatus() == null || !TypeUtil.bigDecimalToBoolean(wrapper.getDetail().getPersonStatus().getId())) {
                throw new MtpAppException(ConstantMd.MSG_PERSON_STATUS_NOT_NULL);
            } else if (wrapper.getDetail().getCitizen() == null || !TypeUtil.strToBoolean(wrapper.getDetail().getCitizen().getName())) {
                throw new MtpAppException(ConstantMd.MSG_CITIZEN_NAME_NOT_NULL);
            } else if (!TypeUtil.strToBoolean(wrapper.getDetail().getCitizen().getSurname())) {
                throw new MtpAppException(ConstantMd.MSG_CITIZEN_SURNAME_NOT_NULL);
            } else if (!TypeUtil.strToBoolean(wrapper.getDetail().getCitizen().getPatronymic())) {
                throw new MtpAppException(ConstantMd.MSG_CITIZEN_PATRONYMIC_NOT_NULL);
            } else if (wrapper.getDetail().getCitizen().getBirthDate() == null) {
                throw new MtpAppException(ConstantMd.MSG_CITIZEN_BIRTH_DATE_NOT_NULL);
            } else if (!TypeUtil.strToBoolean(wrapper.getDetail().getCitizen().getBirthPlace())) {
                throw new MtpAppException(ConstantMd.MSG_CITIZEN_BIRTH_PLACE_NOT_NULL);
            } else if (!TypeUtil.strToBoolean(wrapper.getDetail().getCitizen().getRegAddress())) {
                throw new MtpAppException(ConstantMd.MSG_CITIZEN_REG_PLACE_NOT_NULL);
            } else if (!TypeUtil.strToBoolean(wrapper.getDetail().getCitizen().getPhone())) {
                throw new MtpAppException(ConstantMd.MSG_CITIZEN_PHONE_NOT_NULL);
            } else if (wrapper.getDetail().getCitizen().getGender() == Gender.Unknown) {
                throw new MtpAppException(ConstantMd.MSG_CITIZEN_GENDER_NOT_NULL);
            }

            ProtocolDetailService detailService = (ProtocolDetailService) ServiceFactory.get(ServiceName.PROTOCOL_SERVICE);
            String result = detailService.insertProlDeatil(wrapper.getDetail(), wrapper.getUser());
            if (!result.isEmpty()) {
                throw new MtpAppException(result);
            }
            id = wrapper.getDetail().getId();
        } catch (MtpAppException e) {
            logger.error(e);
            throw e;
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("insertProtocolDetail", e, wrapper.toString()), wrapper.getUser());
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        }
        return id;
    }

    @POST
    @Path("/loginUser")
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public User loginUser(User user) throws MtpAppException {
        try {
            if (user == null) {
                throw new MtpAppException(ConstantMd.MSG_USER_INFO_NOT_NULL);
            } else if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                throw new MtpAppException(ConstantMd.MSG_USER_PASSWORD_NOT_NULL);
            }
            user.getParam().setAppId(Constant.MTP_MD_APP_ID);
            return service.loginUser(user);
        } catch (MtpAppException e) {
            logger.error(e);
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        } catch (Exception e) {
            logger.error(e);
            if (user == null) {
                LogHelper.insertError(new ErrorModel("loginUser", e), Constant.MTP_MD_APP_ID);
            } else {
                LogHelper.insertError(new ErrorModel("loginUser", e, user.toString()), Constant.MTP_MD_APP_ID);
            }
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        }
    }

    @PUT
    @Path("/updatePassword")
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public void updatePassword(User user) throws MtpAppException {

        try {
            if (user == null) {
                throw new MtpAppException(ConstantMd.MSG_USER_INFO_NOT_NULL);
            } else if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                throw new MtpAppException(ConstantMd.MSG_USER_PASSWORD_NOT_NULL);
            } else if (user.getNote() == null || user.getNote().trim().isEmpty()) {
                throw new MtpAppException(ConstantMd.MSG_USER_NEW_PASSWORD_NOT_NULL);
            }
            UserService userService = (UserService) ServiceFactory.get(ServiceName.USER_SERVICE);
            String result = userService.updatePassword(user);
            if (!result.isEmpty()) {
                throw new MtpAppException(result);
            }
        } catch (MtpAppException e) {
            logger.error(e);
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        } catch (Exception e) {
            logger.error(e);
            LogHelper.insertError(new ErrorModel("updatePassword", e, user.toString()), user);
            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public String defaultMedhod() {
        StringBuilder sb = new StringBuilder("Method list").append("\n\r");
        int i = 0;
        for (Method method : RestRS.class.getDeclaredMethods()) {
            sb.append(++i).append(")").append(method.getName()).append("\n\r");
        }
        return sb.toString();
    }
    //rashad

    @GET
    @Path("/getMenuList/{depId}")
    @Produces(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    @Consumes(MediaType.APPLICATION_JSON + ConstantMd.UTF8)
    public List<Menu> getMenuList(@PathParam("depId") BigDecimal depId) throws Exception {
        List<Menu> menuList = new ArrayList<>();
        try {
//            if (user == null) {
//                throw new MtpAppException(ConstantMd.MSG_USER_INFO_NOT_NULL);
//            } else if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
//                throw new MtpAppException(ConstantMd.MSG_USER_PASSWORD_NOT_NULL);
//            }
//            user.getParam().setAppId(Constant.MTP_MD_APP_ID);
            menuList = service.getMenuListByDepId(depId);
//        } catch (MtpAppException e) {
//            logger.error(e);
//            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        } catch (Exception e) {
            logger.error(e);
//            if (user == null) {
//                LogHelper.insertError(new ErrorModel("loginUser", e), Constant.MTP_MD_APP_ID);
//            } else {
//                LogHelper.insertError(new ErrorModel("loginUser", e, user.toString()), Constant.MTP_MD_APP_ID);
//            }
//            throw new MtpAppException(ConstantMd.DEFAULT_EXCEPTION_MESSAGE);
        }
        return menuList;

    }
}
