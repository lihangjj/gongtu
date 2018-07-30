package lz.cm.dao;

import lz.cm.vo.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface IMessageDAO extends IDAO<Integer, Message> {
    boolean sendTo(Map<String, Object> map);

    boolean updateFlag(String messageid);

    boolean plDelete(Map map);

    boolean plljDelete(Map map);

    String getStatus(Map map);

    boolean plDeleteGr(Map map);
    boolean plYdMessage(Map map);



}
