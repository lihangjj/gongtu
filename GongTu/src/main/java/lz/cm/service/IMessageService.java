package lz.cm.service;

import lz.cm.vo.Message;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface IMessageService extends IService<Integer, Message> {
    boolean edit(Message message) throws Exception;

    boolean add(Message message) throws Exception;

    boolean plljDelete(String[] ids) throws Exception;

    /**
     * 批量删除个人收到的消息
     * @param ids
     * @param memberid
     * @return
     * @throws Exception
     */
    boolean plDeleteGr(String[] ids,String memberid) throws Exception;

    @Transactional(propagation = Propagation.REQUIRED)
    boolean sendTo(int messageid, String[] ids) throws Exception;
    Map<String, Object> shoudaoMessage(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue,String memberid) throws Exception;

}
