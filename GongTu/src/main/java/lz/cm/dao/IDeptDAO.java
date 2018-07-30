package lz.cm.dao;

import lz.cm.vo.Dept;
import lz.cm.vo.Job;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IDeptDAO extends IDAO<Integer,Dept> {
    Dept getDeptByDname(String dname);
    boolean delete(Dept dept);
    List<Job> getJobsByDeptId(Integer deptid);

    Dept checkDname(String dname);
}
