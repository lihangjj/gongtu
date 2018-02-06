package lz.cm.dao;

import lz.cm.vo.Log;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ILogDAO extends IDAO<Integer,Log> {
    boolean delete(Log log);
}
