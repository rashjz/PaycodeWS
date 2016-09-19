/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rashjz.info.paycode.app;

import java.math.BigDecimal;
import java.util.logging.Logger;
import rashjz.info.paycode.app.model.User;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import rashjz.info.paycode.app.dao.UserDao;
import rashjz.info.paycode.app.model.Balance;
import rashjz.info.paycode.app.model.UserLog;

/**
 * REST Web Service
 *
 * @author rashjz
 */
@Path("wserv")
public class MobbyResource {

    private static final Logger LOG = Logger.getLogger(MobbyResource.class.getName());

    @Context
    private UriInfo context;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("getInfo")
    public String getInfo() {
        //TODO return proper representation object
        return "Copyright by Rashad Javadov";
    }

    @GET
    @Path("checkLogin")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public User checklogin(@QueryParam("login") String login, @QueryParam("passw") String passw) {
//        http://localhost:8075/PaycodeWS/wserv/checkLogin?login=rashadjavad@gmail.com&passw=12
        LOG.info("login " + login + " passw " + passw);
        User user = new UserDao().checklogin(login, passw);
//        r.setName("Rashad " + role);
        return user;
    }

    @POST
    @Path("updateBalance")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Balance updateBalance(Balance balance) {
          LOG.info("updateBalance invoked " + balance.toString());
        balance = new UserDao().updateBalance(balance); 
        return balance;
    }

    @POST
    @Path("createUser")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public User createUser(User user) {
//        user = new User();
//        user.setEmail("orkhan.yusubov@gmail.com");
//        user.setMobile("9953276324");
//        user.setName("Orxan");
//        user.setPassword("12");
//        user.setPatronymic("1");
//        user.setSurname("Yusubov");

//        http://localhost:8075/PaycodeWS/wserv/checkLogin?login=rashadjavad@gmail.com&passw=12
//        LOG.info(""+user.toString());
        User newuser = new UserDao().registerUser(user);
//        r.setName("Rashad " + role);
        return newuser;
    }

    @POST
    @Path("createUserLog")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
//    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public void createUserLog(UserLog userLog) {
        new UserDao().insertUserLog(userLog);
    }

}
