package lz.cm.service.impl;

import lz.cm.dao.*;
import lz.cm.service.IProjectService;
import lz.cm.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private IProjectDAO projectDAO;
    @Autowired
    private IContractDAO contractDAO;
    @Autowired
    private ILogDAO logDAO;
    @Autowired
    private IMemberDAO memberDAO;
    @Autowired
    private IRencaiDAO rencaiDAO;
    @Autowired
    private ISydwDAO sydwDAO;

    @Override
    public Project getVoById(Integer id) {
        return null;
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        if (parameterValue==null){
            parameterValue="";
        }
        Map<String, Object> resMap = projectDAO.splitVoByFlag(Project.class, null, null, currentPage, lineSize,"  dflag=0 "+parameterValue);
        List<Project> allProject = (List<Project>) resMap.get("allVo");
        setValueForProject(allProject);
        resMap.put("allVo", allProject);
        return resMap;
    }

    private void setValueForProject(List<Project> allProject) {
        for (Project p : allProject) {
            p.setContract(contractDAO.findXxByConditions("contractid,expireDate,signingDate,status,companyName", "contractid=" + p.getContractid(), Contract.class));
            List<Log> logs = (List<Log>) logDAO.splitVoByFlag(Log.class, null, null, 1, null, getCondition("projectid", "=", p.getProjectid().toString()) + " and dflag=0 ").get("allVo");
            List<Rencai> rencais = (List<Rencai>) rencaiDAO.splitVoByColumns("zsdengji,name,rcType,zszhuanye,photo", Rencai.class, null, null, 1, null, " rencaiid in (select rencaiid from rencai_project where projectid='" + p.getProjectid() + "')").get("allVo");
            p.setRencais(rencais);
            for (Log l : logs) {
                l.setMember(memberDAO.getMemberNameByMemberid(l.getMemberid()));
            }
            p.setLogs(logs);
        }
    }
    @Override
    public List<Project> getALLSimpleProject() {
        List<Project> allP = (List<Project>) projectDAO.splitVoByColumns("projectid,type,name,contractid", Project.class, null, null, null, null, " dflag=0").get("allVo");
        for (Project p : allP) {
            p.setContract(contractDAO.findXxByConditions("companyName", " contractid=" + p.getContractid() + " AND dflag=0 ", Contract.class));
        }
        return allP;
    }

    @Override
    public Map<String, Object> splitVoByExcutive(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue, String name) {
        Map<String, Object> resMap = projectDAO.splitVoByFlag(Project.class, null, null, currentPage, lineSize,"  dflag=0 AND executive='"+name+"' "+parameterValue);
        List<Project> allProject = (List<Project>) resMap.get("allVo");
        setValueForProject(allProject);
        resMap.put("allVo", allProject);
        return resMap;
    }

    @Override
    public boolean editProjectStatus(Project project) {
        Project oProject = projectDAO.findXxByConditions("contractid ", "projectid='" + project.getProjectid() + "'", Project.class);
        //先更新项目的状态
        projectDAO.updateXxClomuns("status", project);

        String sql = " SELECT COUNT(*) FROM project WHERE contractid='" + oProject.getContractid() + "'  AND status!='" + project.getStatus() + "'";
        Integer count = projectDAO.getStatusCount(sql);
        if (count > 0) {//还有其他项目没有一致，也许是完成，也暂停，也许是进行中，那就不改合同的状态
            return true;
        } else {//全部数据都一致，修改合同状态
            Contract contract = new Contract();
            contract.setContractid(oProject.getContractid());
            contract.setStatus(project.getStatus());
            if (contractDAO.updateXxClomuns("status", contract)) {
                return true;

            }
            return false;
        }
    }

    @Override
    public boolean plDeleteProject(String[] ids) throws Exception {
        Map<String, Object> pMap = new HashMap<>();
        pMap.put("ids", ids);
        return projectDAO.plDeleteProject(pMap);
    }

    @Override
    public List<Project> getProjectsByContractId(Integer contractid) throws Exception {
        return projectDAO.getAllProjectByConstractId(contractid);
    }

    //将人才增加至项目。也就是说人才也要增加至使用单位
    @Override
    public boolean rencaisAddToProject(String[] ids, Integer projectid) {
        Map<String, Object> pMap = new HashMap<>();
        pMap.put("projectid", projectid);
        String companyName = projectDAO.getCompanyNameByProjectid(projectid);
        String xmName = projectDAO.findById(projectid, Project.class).getName();

        pMap.put("ids", ids);//这是人才ids
        //先删除以前的数据
        projectDAO.rencaisDeleteProject(pMap);
        int x = 0;
        for (String s : ids) {
            pMap.put("rencaiid", s);
            if (projectDAO.rencaisAddToProject(pMap)) {
                Sydw sydw = new Sydw();
                sydw.setSydanwei(companyName);
                sydw.setXmName(xmName);
                sydw.setZzType("");
                sydw.setRencaiid(Integer.valueOf(s));
                if (sydwDAO.insertAll(sydw)) {
                    x++;
                }
            }
        }


        return x == ids.length;
    }

    @Override
    public boolean rencaisFromProjectRemove(String[] ids, Integer projectid) {
        Map<String, Object> pMap = new HashMap<>();
        pMap.put("projectid", projectid);
        String companyName = projectDAO.getCompanyNameByProjectid(projectid);
        String xmName = projectDAO.findById(projectid, Project.class).getName();
        pMap.put("ids", ids);
        for (String s : ids) {
            Sydw sydw = new Sydw();
            sydw.setRencaiid(Integer.valueOf(s));
            sydw.setXmName(xmName);
            sydw.setSydanwei(companyName);
            projectDAO.deleteSydw(sydw);
        }

        //先删除以前的数据
        return projectDAO.rencaisDeleteProject(pMap);
    }

    @Override
    public List<Project> getProjectsByExecutive(String name) {
        List<Project> allProject = projectDAO.getAllProjectByExecutive(name);
        setValueForProject(allProject);
        return allProject;
    }

    @Override
    public List<Project> getAllprojectsByStatus(String status) {


        List<Project> allProject = projectDAO.getAllprojectsByStatus(status);
        setValueForProject(allProject);
        return allProject;
    }

    @Override
    public String getCompanyNameByProjectid(Integer projectid) {
        return projectDAO.getCompanyNameByProjectid(projectid);
    }

}
