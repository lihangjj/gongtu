package lz.cm.service.impl;

import lz.cm.dao.ISydwDAO;
import lz.cm.service.ISydwService;
import lz.cm.vo.Rencai;
import lz.cm.vo.Sydw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SydwServiceImpl implements ISydwService {
    @Autowired
    private ISydwDAO sydwDAO;


    @Override
    public boolean add(Sydw sydw) throws Exception {
        return sydwDAO.insertAll(sydw);
    }

    @Override
    public boolean edit(Sydw sydw) throws Exception {
        return sydwDAO.updateAll(sydw);
    }

    @Override
    public Sydw getVoById(Integer id) {
        return null;
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        return null;
    }
}
