package lz.cm.service.impl;

import lz.cm.dao.IDeptDAO;
import lz.cm.dao.IMemberDAO;
import lz.cm.service.IDeptService;
import lz.cm.vo.Dept;
import lz.cm.vo.Job;
import lz.cm.vo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeptServiceImpl implements IDeptService {

    @Autowired
    private IDeptDAO deptDAO;
    @Autowired
    private IMemberDAO memberDAO;

    @Override
    public boolean edit(Dept dept) throws Exception {
        return deptDAO.doUpdate(dept);
    }

    @Override
    public boolean add(Dept dept) throws Exception {
        return deptDAO.doCreate(dept);
    }

    @Override
    public List<Job> getJobsByDeptId(Integer deptid) throws Exception {
        return deptDAO.getJobsByDeptId(deptid);
    }



    @Override
    public boolean delete(Dept dept) throws Exception {
        return deptDAO.delete(dept);
    }

    @Override
    public boolean checkDname(String dname) throws Exception {
        return deptDAO.checkDname(dname)==null;
    }

    @Override
    public Dept getVoById(Integer id) {
        return deptDAO.findById(id, Dept.class);
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        Map<String, Object> resMap = deptDAO.splitVoByFlag(Dept.class, null, null, null, null, getCondition(null, null, null));
        List<Dept> allDept = (List<Dept>) resMap.get(("allVo"));
        Map<String,Object> parameterMap=new HashMap<>();
        for (Dept d : allDept) {
            List<Job> allJobs = deptDAO.getJobsByDeptId(d.getDeptid());
            Set<Integer> ids = new HashSet<>();
            for (Job j : allJobs) {
                ids.add(j.getJobid());
            }
            if(ids.size()==0){
                d.setRenshu(0);

            }else {
                parameterMap.put("ids",ids);
                d.setRenshu(memberDAO.getDeptRenshuByJobId(parameterMap));
                if (d.getDeptid()==1){
                    d.setRenshu(memberDAO.getDeptRenshuByJobId(parameterMap)-1);

                }
            }
        }
        resMap.put("allVo", allDept);
        return resMap;


    }
}
