/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rashjz.info.paycode.app.model;

import java.math.BigDecimal;

/**
 *
 * @author Mobby
 */
public class Balance {

    private BigDecimal userID;
    private BigDecimal amount;
    private BigDecimal balance;
    private BigDecimal status;

    public Balance() {
        userID = BigDecimal.ZERO;
        amount = BigDecimal.ZERO;
        balance = BigDecimal.ZERO;
        status = BigDecimal.ZERO;
    }

    public BigDecimal getUserID() {
        return userID;
    }

    public void setUserID(BigDecimal userID) {
        this.userID = userID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Balance{" + "userID=" + userID + ", amount=" + amount + ", balance=" + balance + ", status=" + status + '}';
    }

}
