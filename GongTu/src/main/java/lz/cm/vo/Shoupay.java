package lz.cm.vo;

import java.io.Serializable;
import java.util.Date;

public class Shoupay implements Serializable {
    private String mingxi;
    private String note;
    private String accountid;
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;
    private Double yue, shou, pay;
    private Integer shoupayid;
    private Date date;

    public String getMingxi() {
        return mingxi;
    }

    public void setMingxi(String mingxi) {
        this.mingxi = mingxi;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public Double getYue() {
        return yue;
    }

    public void setYue(Double yue) {
        this.yue = yue;
    }

    public Double getShou() {
        return shou;
    }

    public void setShou(Double shou) {
        this.shou = shou;
    }

    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    public Integer getShoupayid() {
        return shoupayid;
    }

    public void setShoupayid(Integer shoupayid) {
        this.shoupayid = shoupayid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
