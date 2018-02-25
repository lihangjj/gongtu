package lz.cm.service.impl;

import lz.cm.dao.IMessageDAO;
import lz.cm.service.IMessageService;
import lz.cm.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements IMessageService {
    @Autowired
    private IMessageDAO messageDAO;


    @Override
    public Message getVoById(Integer id) {
        return messageDAO.findById(id, Message.class);
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        String c = getCondition(parameterName, "=", parameterValue) + " AND dflag=0";
        return messageDAO.splitVoByFlag(Message.class, column, keyWord, currentPage, lineSize, c);
    }

    @Override
    public boolean edit(Message message) throws Exception {
        return messageDAO.doUpdate(message);
    }

    @Override
    public boolean add(Message message) throws Exception {
        return messageDAO.doCreate(message);
    }

    @Override
    public boolean plljDelete(String[] ids) throws Exception {
        Map<String, Object> pMap = new HashMap<>();
        List<String> trueDelete = new ArrayList<>();
        List<String> ljDelete = new ArrayList<>();
        boolean flag1 = false;
        boolean flag2 = false;
        for (String s : ids) {
            Message m = messageDAO.findById(Integer.valueOf(s), Message.class);
            if (m.getFlag() == 0) {//没有发送，可以真的删除
                trueDelete.add(s);

            } else {//已经发送，只能逻辑删除
                ljDelete.add(s);

            }
        }
        if (trueDelete.size() > 0) {//真的删除成功
            pMap.put("ids", trueDelete);
            flag1 = messageDAO.plDelete(pMap);
        } else {
            flag1 = true;
        }
        if (ljDelete.size() > 0) {//逻辑删除成功
            pMap.put("ids", ljDelete);
            flag2 = messageDAO.plljDelete(pMap);
        } else {
            flag2 = true;
        }


        return flag1 && flag2;
    }

    @Override
    public boolean plDeleteGr(String[] ids, String memberid) throws Exception {
        Map<String, Object> pMap = new HashMap<>();
        pMap.put("ids", ids);
        pMap.put("memberid", memberid);
        return messageDAO.plDeleteGr(pMap);
    }


    @Override
    public boolean sendTo(int messageid, String[] ids) throws Exception {
        Map<String, Object> pMap = new HashMap<>();
        pMap.put("messageid", messageid);
        int x = 0;
        for (String s : ids) {
            pMap.put("memberid", s);
            if (messageDAO.sendTo(pMap)) {
                x++;
            }
        }
        if (x == ids.length) {

            return messageDAO.updateFlag(String.valueOf(messageid));
        }


        return false;
    }

    @Override
    public Map<String, Object> shoudaoMessage(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue, String memberid) throws Exception {
        String con = getCondition(parameterName, "=", parameterValue);
        if (!"".equals(con)) {
            con = " AND " + con;
        }
        String c = " messageid IN (select messageid from member_message where memberid='" + memberid + "' " + con + ") ";
        Map<String, Object> resMap = messageDAO.splitVoByFlag(Message.class, column, keyWord, currentPage, lineSize, c);
        List<Message> allMessage = (List<Message>) resMap.get("allVo");
        Map<String, Object> pMap = new HashMap<>();
        pMap.put("memberid", memberid);
        for (Message m : allMessage) {
            pMap.put("messageid", m.getMessageid());
            String staus = messageDAO.getStatus(pMap);
            m.setStatus(staus);
        }
        resMap.put("allVo", allMessage);
        return resMap;
    }


}
