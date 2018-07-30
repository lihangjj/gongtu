package lz.cm.service;

import lz.cm.vo.Dept;
import lz.cm.vo.Job;

import java.util.List;

public interface IDeptService extends IService<Integer, Dept> {

    boolean edit(Dept dept) throws Exception;

    boolean add(Dept dept) throws Exception;

    List<Job> getJobsByDeptId(Integer deptid) throws Exception;

    boolean delete(Dept dept) throws Exception;

    boolean checkDname(String dname) throws Exception;


}
