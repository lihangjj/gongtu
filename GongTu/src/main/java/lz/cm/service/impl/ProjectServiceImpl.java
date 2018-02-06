package lz.cm.service.impl;

import lz.cm.dao.IContractDAO;
import lz.cm.dao.IProjectDAO;
import lz.cm.service.IProjectService;
import lz.cm.vo.Contract;
import lz.cm.vo.Project;
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

    @Override
    public Project getVoById(Integer id) {
        return null;
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        String condition;
        System.err.println(parameterValue);
        if (parameterValue == null || "".equals(parameterValue)) {
            String s = getCondition(parameterName, "=", parameterValue);
            if ("".equals(s)) {
                condition = " dflag=0";
            } else {
                condition = s + " AND dflag=0";
            }
        } else {
            if (parameterValue.contains("-")) {//有状态条件
                String c[] = parameterValue.split("-");
                String[] vals = c[1].split("=");
                if (vals.length == 2) {//,有状态筛选
                    String val = vals[1];
                    if ("".equals(c[0]) || c[0] == null) {//如果类型为所有
                        condition = " contractid in (select contractid from contract where status='" + val + "')";
                    } else {//如果有类型筛选
                        condition = parameterName + "='" + c[0] + "' AND contractid in (select contractid from contract where status='" + val + "')";
                    }

                } else {//无状态筛选
                    if ("".equals(c[0]) || c[0] == null) {//如果类型为所有
                        condition = "";
                    } else {//如果有类型筛选
                        condition = parameterName + "='" + c[0] + "'";
                    }

                }


            } else {
                String s = getCondition(parameterName, "=", parameterValue);
                if ("".equals(s)) {
                    condition = " dflag=0";
                } else {
                    condition = s + " AND dflag=0";
                }

            }

        }


        Map<String, Object> resMap = projectDAO.splitVoByFlag(Project.class, column, keyWord, currentPage, lineSize, condition);
        List<Project> allProject = (List<Project>) resMap.get("allVo");
        for (Project p : allProject) {
            p.setContract(contractDAO.findById(p.getContractid(), Contract.class));
        }
        resMap.put("allVo", allProject);
        return resMap;
    }

    @Override
    public boolean plDeleteProject(String[] ids) throws Exception {
        Map<String, Object> pMap = new HashMap<>();
        pMap.put("ids", ids);
        return projectDAO.plDeleteProject(pMap);
    }
}
