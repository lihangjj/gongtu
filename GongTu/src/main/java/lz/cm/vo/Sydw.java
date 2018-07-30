package lz.cm.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

//使用单位
public class Sydw implements Serializable {
    private Integer sydwid;
    private List<Project> projects;

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public String getXmName() {
        return xmName;
    }

    public void setXmName(String xmName) {
        this.xmName = xmName;
    }

    private Integer rencaiid;


    private String sydanwei, zzType, xmName;
    private Date startTime, overTime;

    public Integer getSydwid() {
        return sydwid;
    }

    public void setSydwid(Integer sydwid) {
        this.sydwid = sydwid;
    }

    public Integer getRencaiid() {
        return rencaiid;
    }

    public void setRencaiid(Integer rencaiid) {
        this.rencaiid = rencaiid;
    }

    public String getSydanwei() {
        return sydanwei;
    }

    public void setSydanwei(String sydanwei) {
        this.sydanwei = sydanwei;
    }

    public String getZzType() {
        return zzType;
    }

    public void setZzType(String zzType) {
        this.zzType = zzType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }
}
