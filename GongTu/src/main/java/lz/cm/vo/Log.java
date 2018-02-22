package lz.cm.vo;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {
    private Integer logid, projectid;
    private String note, coordination, type;
    private Integer dflag;
    private Date time;
    private String memberid;
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCoordination() {
        return coordination;
    }

    public void setCoordination(String coordination) {
        this.coordination = coordination;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDflag() {
        return dflag;
    }

    public void setDflag(Integer dflag) {
        this.dflag = dflag;
    }
}
