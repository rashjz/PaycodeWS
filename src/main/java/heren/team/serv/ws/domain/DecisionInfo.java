package heren.team.serv.ws.domain;

import az.gov.mia.mtp.domain.BaseModel;

/**
 *
 * @author Vusal Hasanli
 */
public class DecisionInfo extends BaseModel{
    private String pSeries;
    private String pNumber;  
    private double amount;
    private String ixmText;
    private String lawText;
    private String invoiceCode;

    public DecisionInfo() {
        this.pSeries = "";
        this.pNumber = "";
        this.amount = 0;
        this.ixmText = "";
        this.lawText = "";
        this.invoiceCode = "";        
    }

    public String getpSeries() {
        return pSeries;
    }

    public void setpSeries(String pSeries) {
        this.pSeries = pSeries;
    }

    public String getpNumber() {
        return pNumber;
    }

    public void setpNumber(String pNumber) {
        this.pNumber = pNumber;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getIxmText() {
        return ixmText;
    }

    public void setIxmText(String ixmText) {
        this.ixmText = ixmText;
    }

    public String getLawText() {
        return lawText;
    }

    public void setLawText(String lawText) {
        this.lawText = lawText;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    @Override
    public String toString() {
        return "DecisionInfo{" +super.toString()+ "pSeries=" + pSeries + ", pNumber=" + pNumber + ", amount=" + amount + ", ixmText=" + ixmText + ", lawText=" + lawText + ", invoiceCode=" + invoiceCode + '}';
    }
    
    

}
