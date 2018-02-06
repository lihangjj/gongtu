package lz.cm.service;

import lz.cm.vo.Dept;
import lz.cm.vo.Job;

import java.util.List;

public interface IDeptService extends IService<Integer, Dept> {

    boolean edit(Dept dept) throws Exception;

    boolean add(Dept dept) throws Exception;

    List<Job> getJobsByDeptId(int deptid) throws Exception;

    Dept getDeptByDname(Dept dept) throws Exception;
    boolean delete(Dept dept)throws Exception;



}
