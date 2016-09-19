package heren.team.serv.ws.domain;

import java.util.Date;

/**
 *
 * @author Vusal Hasanli
 */
public class BallInfo {
   private String carNumber;
   private String pSeries;
   private String pNnumber;      
   private Date calDate;
   private Date expireDate;
   private int ball;

    public BallInfo() {
        this.carNumber = "";
        this.pSeries = "";
        this.pNnumber = "";
        this.calDate = null;
        this.expireDate = null;
        this.ball = 0;
    }  

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getpSeries() {
        return pSeries;
    }

    public void setpSeries(String pSeries) {
        this.pSeries = pSeries;
    }

    public String getpNnumber() {
        return pNnumber;
    }

    public void setpNnumber(String pNnumber) {
        this.pNnumber = pNnumber;
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public int getBall() {
        return ball;
    }

    public void setBall(int ball) {
        this.ball = ball;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "BallInfo{" + "carNumber=" + carNumber + ", pSeries=" + pSeries + ", pNnumber=" + pNnumber + ", calDate=" + calDate + ", expireDate=" + expireDate + ", ball=" + ball + '}';
    }


}
