package lz.cm.dao;

import lz.cm.vo.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ILogDAO extends IDAO<Integer, Log> {
    boolean delete(Log log);

    boolean plDeleteLog(Map<String, Object> map);

}
