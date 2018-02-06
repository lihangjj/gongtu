package lz.cm.service.impl;

import lz.cm.dao.ILogDAO;
import lz.cm.service.ILogService;
import lz.cm.vo.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LogServiceImpl implements ILogService {
    @Autowired
    private ILogDAO logDAO;

    @Override
    public boolean edit(Log log) throws Exception {
        return false;
    }

    @Override
    public boolean add(Log log) throws Exception {
        return logDAO.doCreate(log);
    }

    @Override
    public boolean delete(Log log) throws Exception {
        return false;
    }

    @Override
    public Log getVoById(Integer id) {
        return null;
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        return null;
    }
}
