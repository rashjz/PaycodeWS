package heren.team.serv.ws.domain;

import az.gov.mia.mtp.domain.ProtocolDetail;
import az.gov.mia.mtp.domain.security.User;

/**
 *
 * @author Vusal Hasanli
 */
public class ProtocolDetailWrapper {
    private ProtocolDetail detail;
    private User user;

    public ProtocolDetailWrapper(ProtocolDetail detail, User user) {
        this.detail = detail;
        this.user = user;
    }

    public ProtocolDetailWrapper() {
        this.detail = new ProtocolDetail();
        this.user = null;
    }

    public ProtocolDetail getDetail() {
        return detail;
    }

    public void setDetail(ProtocolDetail detail) {
        this.detail = detail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ProtocolDetailWrapper{" + "detail=" + detail + ", user=" + user + '}';
    }

}
