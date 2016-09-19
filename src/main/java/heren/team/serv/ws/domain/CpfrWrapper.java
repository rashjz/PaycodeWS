package heren.team.serv.ws.domain;

import az.gov.mia.mtp.domain.security.User;
import java.math.BigDecimal;

/**
 *
 * @author Vusal Hasanli
 */
public class CpfrWrapper {
    private BigDecimal radarId;
    private User user;

    public CpfrWrapper(BigDecimal radarId, User user) {
        this.radarId = radarId;
        this.user = user;
    }

    public CpfrWrapper() {
        this.radarId = new BigDecimal(0);
        this.user = null;
    }

    public BigDecimal getRadarId() {
        return radarId;
    }

    public void setRadarId(BigDecimal radarId) {
        this.radarId = radarId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CpfrWrapper{" + "radarId=" + radarId + ", user=" + user + '}';
    }
}
