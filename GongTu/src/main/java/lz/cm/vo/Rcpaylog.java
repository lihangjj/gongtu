package lz.cm.vo;

import java.io.Serializable;
import java.util.Date;

public class Rcpaylog implements Serializable {
    private Integer rcpaylogid, rencaiid, payCost ;
    private String note, payWay, payAccount, payBank,shouAccount,shouBank ;
    private Date time;

    public Integer getRcpaylogid() {
        return rcpaylogid;
    }

    public void setRcpaylogid(Integer rcpaylogid) {
        this.rcpaylogid = rcpaylogid;
    }

    public Integer getRencaiid() {
        return rencaiid;
    }

    public void setRencaiid(Integer rencaiid) {
        this.rencaiid = rencaiid;
    }

    public Integer getPayCost() {
        return payCost;
    }

    public void setPayCost(Integer payCost) {
        this.payCost = payCost;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getPayBank() {
        return payBank;
    }

    public void setPayBank(String payBank) {
        this.payBank = payBank;
    }

    public String getShouAccount() {
        return shouAccount;
    }

    public void setShouAccount(String shouAccount) {
        this.shouAccount = shouAccount;
    }

    public String getShouBank() {
        return shouBank;
    }

    public void setShouBank(String shouBank) {
        this.shouBank = shouBank;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
