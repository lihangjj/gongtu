package lz.cm.service;

import lz.cm.vo.Project;

public interface IProjectService extends IService<Integer, Project> {
    boolean plDeleteProject(String[] ids) throws Exception;


}
