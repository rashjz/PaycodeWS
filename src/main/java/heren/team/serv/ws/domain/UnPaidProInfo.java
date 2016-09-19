package heren.team.serv.ws.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vusal Hasanli
 */
public class UnPaidProInfo {
    private BigDecimal id;
    private List<String> ixmDescriptionList;
    private int ball;
    private int speedReal; 
    private int speedMax; 

    public UnPaidProInfo() {
        this.id = new BigDecimal(0);
        this.ixmDescriptionList = new ArrayList<>();
        this.ball = 0;
        this.speedReal = 0;
        this.speedMax = 0;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }
  
    public List<String> getIxmDescriptionList() {
        return ixmDescriptionList;
    }

    public void setIxmDescriptionList(List<String> ixmDescriptionList) {
        this.ixmDescriptionList = ixmDescriptionList;
    }

    public int getBall() {
        return ball;
    }

    public void setBall(int ball) {
        this.ball = ball;
    }

    public int getSpeedReal() {
        return speedReal;
    }

    public void setSpeedReal(int speedReal) {
        this.speedReal = speedReal;
    }

    public int getSpeedMax() {
        return speedMax;
    }

    public void setSpeedMax(int speedMax) {
        this.speedMax = speedMax;
    }

    @Override
    public String toString() {
        return "UnPaidProInfo{" + "id=" + id + ", ixmDescriptionList=" + ixmDescriptionList + ", ball=" + ball + ", speedReal=" + speedReal + ", speedMax=" + speedMax + '}';
    }

}
