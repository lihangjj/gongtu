package lz.cm.service.impl;

import lz.cm.dao.IContractDAO;
import lz.cm.dao.ILogDAO;
import lz.cm.dao.IMemberDAO;
import lz.cm.dao.IProjectDAO;
import lz.cm.service.ILogService;
import lz.cm.vo.Contract;
import lz.cm.vo.Log;
import lz.cm.vo.Member;
import lz.cm.vo.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogServiceImpl implements ILogService {


    @Autowired
    private ILogDAO logDAO;
    @Autowired
    private IProjectDAO projectDAO;
    @Autowired
    private IContractDAO contractDAO;
    @Autowired
    private IMemberDAO memberDAO;

    @Override
    public boolean edit(Log log) throws Exception {
        return logDAO.doUpdate(log);
    }

    @Override
    public boolean add(Log log) throws Exception {
        return logDAO.doCreate(log);
    }

    @Override
    public boolean plDeleteLog(String[] ids) throws Exception {
        Map<String, Object> pMap = new HashMap<>();
        pMap.put("ids", ids);
        return logDAO.plDeleteLog(pMap);
    }

    @Override
    public Map<String, Object> splitByExrcutive(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue, String executive) throws Exception {

        if (parameterValue.contains("split")) {
            String status = parameterValue.split("split")[1].split("=")[1];

            parameterValue = parameterValue.split("split")[0] + " and projectid in (select projectid from project where contractid in (select contractid from contract where status='" + status + "'))";

        }
        parameterValue = parameterValue + " and memberid='" + executive + "' order by time desc";


        Map<String, Object> resMap = logDAO.splitVoByFlag(Log.class, column, keyWord, currentPage, lineSize, parameterValue);
        List<Log> allLogs = (List<Log>) resMap.get("allVo");
        for (Log l : allLogs) {
            l.setMember(memberDAO.getMemberNameByMemberid(l.getMemberid()));
            if (l.getProjectid() == null) {
                l.setProject(null);
            } else {
                Project p = projectDAO.findById(l.getProjectid(), Project.class);
                if (p != null) {
                    p.setContract(contractDAO.findById(p.getContractid(), Contract.class));

                }
                l.setProject(p);
            }

        }
        resMap.put("allVo", allLogs);
        return resMap;
    }


    @Override
    public Log getVoById(Integer id) {
        return null;
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {

        if (parameterValue.contains("split")) {
            String status = parameterValue.split("split")[1].split("=")[1];

            parameterValue = parameterValue.split("split")[0] + " and projectid in (select projectid from project  where status='" + status + "')";

        }
        parameterValue = parameterValue + " order by time desc";

        Map<String, Object> resMap = logDAO.splitVoByFlag(Log.class, column, keyWord, currentPage, lineSize, parameterValue);
        List<Log> allLogs = (List<Log>) resMap.get("allVo");
        for (Log l : allLogs) {
            l.setMember(memberDAO.getMemberNameByMemberid(l.getMemberid()));
            if (l.getProjectid() == null) {
                l.setProject(null);
            } else {
                Project p = projectDAO.findById(l.getProjectid(), Project.class);
                if (p != null) {
                    p.setContract(contractDAO.findById(p.getContractid(), Contract.class));

                }

                l.setProject(p);
            }

        }
        resMap.put("allVo", allLogs);
        return resMap;
    }
}
