package lz.cm.service.impl;

import lz.cm.dao.IRcpaylogDAO;
import lz.cm.dao.IRencaiDAO;
import lz.cm.service.IRcpaylogService;
import lz.cm.vo.Rcpaylog;
import lz.cm.vo.Rencai;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RcpaylogServiceImpl implements IRcpaylogService {
    @Autowired
    private IRcpaylogDAO rcpaylogDAO;
    @Autowired
    private IRencaiDAO rencaiDAO;


    @Override
    public boolean add(Rcpaylog log) throws Exception {
        if (rcpaylogDAO.insertAll(log)) {
            return rencaiDAO.editAlreadyPay(log.getRencaiid());
        }
        return false;
    }

    @Override
    public List<Rcpaylog> getRcpaylogByRencaiid(Integer rencaiid) throws Exception {
        return (List<Rcpaylog>) rcpaylogDAO
                .splitVoByFlag(Rcpaylog.class, null, null, null, null, " rencaiid=" + rencaiid.toString())
                .get("allVo");
    }

    @Override
    public Rcpaylog getVoById(Integer id) {
        return null;
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        return null;
    }
}
