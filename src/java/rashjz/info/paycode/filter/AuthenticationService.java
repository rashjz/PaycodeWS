/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rashjz.info.paycode.filter;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Rashad Javadov
 */
public class AuthenticationService {

    private static final Logger LOG = Logger.getLogger(AuthenticationService.class.getName());

    public boolean authenticate(String authCredentials) {
        boolean authenticationStatus = false;
        if (null == authCredentials) {
            return false;
        }
        final String encodedUserPassword = authCredentials.replaceFirst("Basic" + " ", "");
        String usernameAndPassword = null;
        try {
            byte[] decodedBytes = DatatypeConverter.parseBase64Binary(encodedUserPassword);
            usernameAndPassword = new String(decodedBytes, "UTF-8");

            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");

            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();
            LOG.info("tokenizer :::  " + username + "  ::::::: " + password);
         
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return authenticationStatus;
    }
}
