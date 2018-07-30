package lz.cm.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Rencai implements Serializable {
    private Integer rencaiid;
    private Integer age;
    private Integer rck;
    private Integer qyqx;
    private Integer youb;
    private Integer youzc;

    public Integer getYoub() {
        return youb;
    }

    public void setYoub(Integer youb) {
        this.youb = youb;
    }

    public Integer getYouzc() {
        return youzc;
    }

    public void setYouzc(Integer youzc) {
        this.youzc = youzc;
    }

    public String getQyren() {
        return qyren;
    }

    public void setQyren(String qyren) {
        this.qyren = qyren;
    }

    private String qyren;
    private Integer qyPrice;
    private Integer fkpc;
    private Integer dflag;
    private List<Sydw> sydw;
    private List<Rcpaylog> rcpaylogList;

    public List<Rcpaylog> getRcpaylogList() {
        return rcpaylogList;
    }

    public void setRcpaylogList(List<Rcpaylog> rcpaylogList) {
        this.rcpaylogList = rcpaylogList;
    }

    public List<Sydw> getSydw() {
        return sydw;
    }

    public void setSydw(List<Sydw> sydw) {
        this.sydw = sydw;
    }

    public Integer getAlreadyPay() {
        return alreadyPay;
    }

    public void setAlreadyPay(Integer alreadyPay) {
        this.alreadyPay = alreadyPay;
    }

    private Integer alreadyPay;
    private String rcType;

    public String getRcType() {
        return rcType;
    }

    public void setRcType(String rcType) {
        this.rcType = rcType;
    }

    private String name;

    public String getBzbg() {
        return bzbg;
    }

    public void setBzbg(String bzbg) {
        this.bzbg = bzbg;
    }

    private String bzbg;
    private String idCard;
    private String phone;
    private String sfzrk;
    private String sfzbf;
    private String sex;
    private String shebao;
    private String kaob;
    private String guaxm;
    private String chuchang;
    private String zczsbg;
    private String zsbg;
    private String yzbg;
    private String hongtou;
    private String psbiao;
    private String jpzm;
    private String zydd;
    private String yjzm;
    private String zsly;
    private String zszhuanye;
    private String zsdengji;
    private String xlzhuanye;


    private String rczhm;
    private String rckhh;
    private String rczh;
    private String photo;
    private String bigPhoto;
    private String zjName;
    private String zjzhm;
    private String zjkhh;
    private String zjzh;
    private String zjlxr;
    private String zjlxrPhone;
    private String hetong;

    public String getXldengji() {
        return xldengji;
    }

    public void setXldengji(String xldengji) {
        this.xldengji = xldengji;
    }

    private String xldengji;

    public Integer getRencaiid() {
        return rencaiid;
    }

    public void setRencaiid(Integer rencaiid) {
        this.rencaiid = rencaiid;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getRck() {
        return rck;
    }

    public void setRck(Integer rck) {
        this.rck = rck;
    }

    public Integer getQyqx() {
        return qyqx;
    }

    public void setQyqx(Integer qyqx) {
        this.qyqx = qyqx;
    }

    public Integer getQyPrice() {
        return qyPrice;
    }

    public void setQyPrice(Integer qyPrice) {
        this.qyPrice = qyPrice;
    }

    public Integer getFkpc() {
        return fkpc;
    }

    public void setFkpc(Integer fkpc) {
        this.fkpc = fkpc;
    }

    public Integer getDflag() {
        return dflag;
    }

    public void setDflag(Integer dflag) {
        this.dflag = dflag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSfzrk() {
        return sfzrk;
    }

    public void setSfzrk(String sfzrk) {
        this.sfzrk = sfzrk;
    }

    public String getSfzbf() {
        return sfzbf;
    }

    public void setSfzbf(String sfzbf) {
        this.sfzbf = sfzbf;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getShebao() {
        return shebao;
    }

    public void setShebao(String shebao) {
        this.shebao = shebao;
    }

    public String getKaob() {
        return kaob;
    }

    public void setKaob(String kaob) {
        this.kaob = kaob;
    }

    public String getGuaxm() {
        return guaxm;
    }

    public void setGuaxm(String guaxm) {
        this.guaxm = guaxm;
    }

    public String getChuchang() {
        return chuchang;
    }

    public void setChuchang(String chuchang) {
        this.chuchang = chuchang;
    }

    public String getZczsbg() {
        return zczsbg;
    }

    public void setZczsbg(String zczsbg) {
        this.zczsbg = zczsbg;
    }

    public String getZsbg() {
        return zsbg;
    }

    public void setZsbg(String zsbg) {
        this.zsbg = zsbg;
    }

    public String getYzbg() {
        return yzbg;
    }

    public void setYzbg(String yzbg) {
        this.yzbg = yzbg;
    }

    public String getHongtou() {
        return hongtou;
    }

    public void setHongtou(String hongtou) {
        this.hongtou = hongtou;
    }

    public String getPsbiao() {
        return psbiao;
    }

    public void setPsbiao(String psbiao) {
        this.psbiao = psbiao;
    }

    public String getJpzm() {
        return jpzm;
    }

    public void setJpzm(String jpzm) {
        this.jpzm = jpzm;
    }

    public String getZydd() {
        return zydd;
    }

    public void setZydd(String zydd) {
        this.zydd = zydd;
    }

    public String getYjzm() {
        return yjzm;
    }

    public void setYjzm(String yjzm) {
        this.yjzm = yjzm;
    }

    public String getZsly() {
        return zsly;
    }

    public void setZsly(String zsly) {
        this.zsly = zsly;
    }

    public String getZszhuanye() {
        return zszhuanye;
    }

    public void setZszhuanye(String zszhuanye) {
        this.zszhuanye = zszhuanye;
    }

    public String getZsdengji() {
        return zsdengji;
    }

    public void setZsdengji(String zsdengji) {
        this.zsdengji = zsdengji;
    }

    public String getXlzhuanye() {
        return xlzhuanye;
    }

    public void setXlzhuanye(String xlzhuanye) {
        this.xlzhuanye = xlzhuanye;
    }

    public String getRczhm() {
        return rczhm;
    }

    public void setRczhm(String rczhm) {
        this.rczhm = rczhm;
    }

    public String getRckhh() {
        return rckhh;
    }

    public void setRckhh(String rckhh) {
        this.rckhh = rckhh;
    }

    public String getRczh() {
        return rczh;
    }

    public void setRczh(String rczh) {
        this.rczh = rczh;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBigPhoto() {
        return bigPhoto;
    }

    public void setBigPhoto(String bigPhoto) {
        this.bigPhoto = bigPhoto;
    }

    public String getZjName() {
        return zjName;
    }

    public void setZjName(String zjName) {
        this.zjName = zjName;
    }

    public String getZjzhm() {
        return zjzhm;
    }

    public void setZjzhm(String zjzhm) {
        this.zjzhm = zjzhm;
    }

    public String getZjkhh() {
        return zjkhh;
    }

    public void setZjkhh(String zjkhh) {
        this.zjkhh = zjkhh;
    }

    public String getZjzh() {
        return zjzh;
    }

    public void setZjzh(String zjzh) {
        this.zjzh = zjzh;
    }

    public String getZjlxr() {
        return zjlxr;
    }

    public void setZjlxr(String zjlxr) {
        this.zjlxr = zjlxr;
    }

    public String getZjlxrPhone() {
        return zjlxrPhone;
    }

    public void setZjlxrPhone(String zjlxrPhone) {
        this.zjlxrPhone = zjlxrPhone;
    }

    public String getHetong() {
        return hetong;
    }

    public void setHetong(String hetong) {
        this.hetong = hetong;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getSfzyxq() {
        return sfzyxq;
    }

    public void setSfzyxq(Date sfzyxq) {
        this.sfzyxq = sfzyxq;
    }

    public Date getNsTime() {
        return nsTime;
    }

    public void setNsTime(Date nsTime) {
        this.nsTime = nsTime;
    }

    public Date getQyTime() {
        return qyTime;
    }

    public void setQyTime(Date qyTime) {
        this.qyTime = qyTime;
    }

    public Date getDqTime() {
        return dqTime;
    }

    public void setDqTime(Date dqTime) {
        this.dqTime = dqTime;
    }

    private Date birthday, sfzyxq, nsTime, qyTime, dqTime;


}
