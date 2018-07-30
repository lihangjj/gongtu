package lz.cm.vo;

import java.io.Serializable;

public class Account implements Serializable {
    private String accountid, name, bank;
    private Double yue;

    public Double getQichuYue() {
        return qichuYue;
    }

    public void setQichuYue(Double qichuYue) {
        this.qichuYue = qichuYue;
    }

    private Double qichuYue;

    public Double getYue() {
        return yue;
    }

    public void setYue(Double yue) {
        this.yue = yue;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
