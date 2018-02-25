package lz.cm.vo;

import java.io.Serializable;
import java.util.Date;

public class Cost implements Serializable {
    private Double costid, heji, fangzu, shui, dian, qiche, canyin, haocai, shebei, tuiguang;
    private Date date;

    public Integer getDflag() {
        return dflag;
    }

    public void setDflag(Integer dflag) {
        this.dflag = dflag;
    }

    private Integer dflag;


    public Double getCostid() {
        return costid;
    }

    public void setCostid(Double costid) {
        this.costid = costid;
    }

    public Double getHeji() {
        return heji;
    }

    public void setHeji(Double heji) {
        this.heji = heji;
    }

    public Double getFangzu() {
        return fangzu;
    }

    public void setFangzu(Double fangzu) {
        this.fangzu = fangzu;
    }

    public Double getShui() {
        return shui;
    }

    public void setShui(Double shui) {
        this.shui = shui;
    }

    public Double getDian() {
        return dian;
    }

    public void setDian(Double dian) {
        this.dian = dian;
    }

    public Double getQiche() {
        return qiche;
    }

    public void setQiche(Double qiche) {
        this.qiche = qiche;
    }

    public Double getCanyin() {
        return canyin;
    }

    public void setCanyin(Double canyin) {
        this.canyin = canyin;
    }

    public Double getHaocai() {
        return haocai;
    }

    public void setHaocai(Double haocai) {
        this.haocai = haocai;
    }

    public Double getShebei() {
        return shebei;
    }

    public void setShebei(Double shebei) {
        this.shebei = shebei;
    }

    public Double getTuiguang() {
        return tuiguang;
    }

    public void setTuiguang(Double tuiguang) {
        this.tuiguang = tuiguang;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
