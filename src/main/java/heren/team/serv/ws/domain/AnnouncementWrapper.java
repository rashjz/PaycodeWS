package heren.team.serv.ws.domain;

import az.gov.mia.mtp.domain.Announcement;
import az.gov.mia.mtp.domain.security.User;

/**
 *
 * @author Vusal Hasanli
 */
public class AnnouncementWrapper {
    private Announcement announcement;
    private User user;

    public AnnouncementWrapper() {
        this.announcement = new Announcement();
        this.user = null;
    }

    public AnnouncementWrapper(Announcement announcement, User user) {
        this.announcement = announcement;
        this.user = user;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
    @Override
    public String toString() {
        return "AnnouncementWrapper{" + "announcement=" + announcement + ", user=" + user + '}';
    }
   
}
