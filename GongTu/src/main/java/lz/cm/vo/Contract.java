package lz.cm.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Contract implements Serializable {
    private String companyName;
    private String contact;
    private String phone;
    private String qq;
    private String principal;
    private String address;
    private String note;
    private String status;
    private String principalPhone;
    List<Project> projects;

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    private Integer allCost;
    private Integer alreadyPay;

    public Integer getDflag() {
        return dflag;
    }

    public void setDflag(Integer dflag) {
        this.dflag = dflag;
    }

    private Integer dflag;

    public Integer getAllCost() {
        return allCost;
    }

    public void setAllCost(Integer allCost) {
        this.allCost = allCost;
    }

    public Integer getAlreadyPay() {
        return alreadyPay;
    }

    public void setAlreadyPay(Integer alreadyPay) {
        this.alreadyPay = alreadyPay;
    }

    public String getPrincipalPhone() {
        return principalPhone;
    }

    public void setPrincipalPhone(String principalPhone) {
        this.principalPhone = principalPhone;
    }

    public String getPrincipalQQ() {
        return principalQQ;
    }

    public void setPrincipalQQ(String principalQQ) {
        this.principalQQ = principalQQ;
    }

    private String principalQQ;
    private Integer contractid,period;
    private Date signingDate;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getContractid() {
        return contractid;
    }

    public void setContractid(Integer contractid) {
        this.contractid = contractid;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Date getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    private Date expireDate;
}
