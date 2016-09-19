package heren.team.serv.ws.domain;

import az.gov.mia.mtp.domain.BaseModel;
import java.math.BigDecimal;

/**
 *
 * @author Vusal Hasanli
 */
public class ProtocolDetailInfo extends BaseModel{
    private String series;
    private String number;
    private String invoiceCode;
    private String text1;
    private String text2;
    private boolean takeDl;
    private double  amount;
    private BigDecimal strId;
    private String strName;

    public ProtocolDetailInfo() {
        this.series = "";
        this.number = "";
        this.invoiceCode = "";
        this.text1 = "";
        this.text2 = "";
        this.takeDl = false;
        this.amount = 0;
        this.strId = new BigDecimal(0);
        this.strName = "";
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public boolean isTakeDl() {
        return takeDl;
    }

    public void setTakeDl(boolean takeDl) {
        this.takeDl = takeDl;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BigDecimal getStrId() {
        return strId;
    }

    public void setStrId(BigDecimal strId) {
        this.strId = strId;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    @Override
    public String toString() {
        return "ProtocolDetailInfo{"+super.toString() + "series=" + series + ", number=" + number + ", invoiceCode=" + invoiceCode + ", text1=" + text1 + ", text2=" + text2 + ", takeDl=" + takeDl + ", amount=" + amount + ", strId=" + strId + ", strName=" + strName + '}';
    }
    
    
    
    
}
