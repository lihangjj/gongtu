package lz.cm.dao;

import lz.cm.vo.Cost;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ICostDAO extends IDAO<Integer,Cost> {



    Integer plDeleteCost(Map<String, Object> pMap);
}
