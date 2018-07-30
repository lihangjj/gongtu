package lz.cm.dao;

import lz.cm.vo.Action;
import lz.cm.vo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IRoleDAO extends IDAO<Integer, Role> {

    List<Action> getAllActionsByRoleId(Integer roleid);

    List<Integer> getAllActionIdByRoleId(Integer roleid);

    boolean actionsAddTo(Map<String, Object> map);

    boolean actionsRemoveFrom(Map<String, Object> map);

    /**
     *
     * @param flag 权限标记，包含了职位名称
     * @return
     */
    String[] getRoleByJobId(String flag);
}
