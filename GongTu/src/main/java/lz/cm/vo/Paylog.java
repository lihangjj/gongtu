package lz.cm.vo;

import java.io.Serializable;
import java.util.Date;

public class Paylog implements Serializable {
    private Integer paylogid,contractid;
    public Integer getPaylogid() {
        return paylogid;
    }

    public void setPaylogid(Integer paylogid) {
        this.paylogid = paylogid;
    }

    public Integer getContractid() {
        return contractid;
    }

    public void setContractid(Integer contractid) {
        this.contractid = contractid;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getPaybank() {
        return paybank;
    }

    public void setPaybank(String paybank) {
        this.paybank = paybank;
    }

    public String getPayaccount() {
        return payaccount;
    }

    public void setPayaccount(String payaccount) {
        this.payaccount = payaccount;
    }

    public String getShoubank() {
        return shoubank;
    }

    public void setShoubank(String shoubank) {
        this.shoubank = shoubank;
    }

    public String getShouaccount() {
        return shouaccount;
    }

    public void setShouaccount(String shouaccount) {
        this.shouaccount = shouaccount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getWkjCost() {
        return wkjCost;
    }

    public void setWkjCost(Integer wkjCost) {
        this.wkjCost = wkjCost;
    }

    public Integer getInvoiceCost() {
        return invoiceCost;
    }

    public void setInvoiceCost(Integer invoiceCost) {
        this.invoiceCost = invoiceCost;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getYkptime() {
        return ykptime;
    }

    public void setYkptime(Date ykptime) {
        this.ykptime = ykptime;
    }

    private String payway,paybank,payaccount,shoubank,shouaccount,note;
    private Integer cost,wkjCost,invoiceCost;
    private Date time,ykptime;


}
