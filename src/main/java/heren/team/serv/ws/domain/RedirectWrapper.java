package heren.team.serv.ws.domain;

import az.gov.mia.mtp.domain.Redirect;
import az.gov.mia.mtp.domain.security.User;

/**
 *
 * @author Vusal Hasanli
 */
public class RedirectWrapper {
    private Redirect redirect;
    private User user;

    public RedirectWrapper(Redirect redirect, User user) {
        this.redirect = redirect;
        this.user = user;
    }

    public RedirectWrapper() {
        this.redirect = new Redirect();
        this.user = null;
    }

    public Redirect getRedirect() {
        return redirect;
    }

    public void setRedirect(Redirect redirect) {
        this.redirect = redirect;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "RedirectWrapper{" + "redirect=" + redirect + ", user=" + user + '}';
    }
        
}
