package lz.cm.dao;

import lz.cm.vo.Project;
import lz.cm.vo.Sydw;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IProjectDAO extends IDAO<Integer, Project> {

    List<Project> getAllProjectByConstractId(Integer contractid);

    boolean plDeleteProjectByContractId(Map<String, Object> map);

    boolean plDeleteProject(Map<String, Object> map);

    boolean rencaisAddToProject(Map<String, Object> pMap);

    boolean rencaisDeleteProject(Map<String, Object> pMap);

    boolean doDeleteProjectByContractId(Integer contractid);

    List<Project> getAllProjectByExecutive(String executive);

    List<Project> getAllprojectsByStatus(String status);

    String getCompanyNameByProjectid(Integer projectid);

    boolean deleteSydw(Sydw sydw);

    Integer getStatusCount(String sql);
}
