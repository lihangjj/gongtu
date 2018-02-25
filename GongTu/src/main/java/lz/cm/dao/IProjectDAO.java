package lz.cm.dao;

import lz.cm.vo.Project;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IProjectDAO extends IDAO<Integer, Project> {

    List<Project> getAllProjectByConstractId(Integer contractid);

    boolean plDeleteProjectByContractId(Map<String, Object> map);

    boolean plDeleteProject(Map<String, Object> map);

}
