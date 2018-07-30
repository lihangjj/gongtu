package lz.cm.service;

import lz.cm.vo.Project;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface IProjectService extends IService<Integer, Project> {
    boolean plDeleteProject(String[] ids) throws Exception;

    List<Project> getProjectsByContractId(Integer contractid) throws Exception;

    @Transactional(propagation = Propagation.REQUIRED)
    boolean rencaisAddToProject(String[] ids, Integer projectid) throws Exception;

    boolean rencaisFromProjectRemove(String[] ids, Integer projectid);

    List<Project> getProjectsByExecutive(String name);

    List<Project> getAllprojectsByStatus(String status);
    String getCompanyNameByProjectid(Integer projectid);
    /**
     * 只是得到项目名称，公司名称用来写日志的时候选择用，不用查询很多
     * @return
     */
    List<Project> getALLSimpleProject();

    /**
     * 普通员工的项目列表依然可以筛选，只是多了个执行人的条件
     * @param column
     * @param keyWord
     * @param currentPage
     * @param lineSize
     * @param parameterName
     * @param parameterValue
     * @param name
     * @return
     */
    Map<String,Object> splitVoByExcutive(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue, String name);

    boolean editProjectStatus(Project project);
}
