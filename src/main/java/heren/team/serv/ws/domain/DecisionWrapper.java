package heren.team.serv.ws.domain;

import az.gov.mia.mtp.domain.Decision;
import az.gov.mia.mtp.domain.security.User;

/**
 *
 * @author Vusal Hasanli
 */
public class DecisionWrapper {
    private Decision decision;
    private User user;

    public DecisionWrapper(Decision decision, User user) {
        this.decision = decision;
        this.user = user;
    }

    public DecisionWrapper() {
        this.decision = new Decision();
        this.user = null;        
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }        

    @Override
    public String toString() {
        return "DecisionWrapper{" + "decision=" + decision + ", user=" + user + '}';
    }

}
