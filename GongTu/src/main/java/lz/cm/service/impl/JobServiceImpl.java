package lz.cm.service.impl;

import lz.cm.dao.IDeptDAO;
import lz.cm.dao.IJobDAO;
import lz.cm.dao.IMemberDAO;
import lz.cm.service.IJobService;
import lz.cm.vo.Dept;
import lz.cm.vo.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JobServiceImpl implements IJobService {
    @Autowired
    private IJobDAO jobDAO;
    @Autowired
    private IDeptDAO deptDAO;
    @Autowired
    private IMemberDAO memberDAO;

    @Override
    public boolean edit(Job job) throws Exception {
        return jobDAO.doUpdate(job);
    }

    @Override
    public boolean add(Job job) throws Exception {
        return jobDAO.doCreate(job);
    }


    @Override
    public boolean delete(Job job) throws Exception {
        if (memberDAO.getJobRenshu(job.getJobid()) > 0) {
            return false;
        }
        return jobDAO.delete(job);
    }

    @Override
    public Job getVoById(Integer id) {
        return null;
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        Map<String, Object> resMap = jobDAO.splitVoByFlag(Job.class, null, null, null, null, getCondition(null, null, null));
        List<Job> allJob = (List<Job>) resMap.get("allVo");
        for (Job j : allJob) {
            j.setDept(deptDAO.findById(j.getDeptid(), Dept.class));
        }
        resMap.put("allVo", allJob);
        resMap.put("allDept", deptDAO.splitVoByFlag(Dept.class, null, null, null, null, getCondition(null, null, null)).get("allVo"));
        return resMap;
    }
}
