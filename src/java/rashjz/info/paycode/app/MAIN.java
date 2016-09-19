/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rashjz.info.paycode.app;

import com.google.gson.Gson;
import java.math.BigDecimal;
import rashjz.info.paycode.app.model.Balance;
import rashjz.info.paycode.app.model.User;

/**
 *
 * @author Mobby
 */
public class MAIN {
    
    public static void main(String[] args) {
        User user = new User();
        user.setEmail("orkhan.yusubov@gmail.com");
        user.setMobile("9953276324");
        user.setName("Orxan");
        user.setPassword("12");
        user.setPatronymic("1");
        user.setSurname("Yusubov");
        
        Gson gson = new Gson();
        String json = gson.toJson(user);
        System.out.println(json);
        
        Balance balance = new Balance();
        balance.setUserID(BigDecimal.ONE);
        balance.setAmount(BigDecimal.TEN);
                
        String json2 = gson.toJson(balance);
        System.out.println(json2);
        
    }
}
