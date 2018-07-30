package lz.cm.service.impl;

import lz.cm.dao.ICostDAO;
import lz.cm.service.ICostService;
import lz.cm.vo.Cost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CostServiceImpl implements ICostService {
    @Autowired
    private ICostDAO costDAO;

    @Override
    public boolean edit(Cost cost) throws Exception {
        return costDAO.doCreate(cost);
    }

    @Override
    public boolean add(Cost cost) throws Exception {
        return costDAO.doCreate(cost);
    }

    @Override
    public boolean plDeleteCost(String[] ids) throws Exception {
        Map<String, Object> pMap = new HashMap<>();
        pMap.put("ids", ids);
        return costDAO.plDeleteCost(pMap)==ids.length;
    }


    @Override
    public Cost getVoById(Integer id) {
        return null;
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        String s = parameterName + " like " + parameterValue + " And dflag=0";

        return costDAO.splitVoByFlag(Cost.class, column, keyWord, currentPage, lineSize, s);
    }
}
