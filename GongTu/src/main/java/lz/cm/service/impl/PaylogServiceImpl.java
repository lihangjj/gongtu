package lz.cm.service.impl;

import lz.cm.dao.IContractDAO;
import lz.cm.dao.IPaylogDAO;
import lz.cm.service.IPaylogService;
import lz.cm.vo.Contract;
import lz.cm.vo.Paylog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaylogServiceImpl implements IPaylogService {
    @Autowired
    private IPaylogDAO paylogDAO;
    @Autowired
    private IContractDAO contractDAO;

    @Override
    public boolean edit(Paylog paylog) throws Exception {
        return false;
    }

    @Override
    public boolean add(Paylog paylog) throws Exception {
        if (paylogDAO.doCreate(paylog)) {
            return contractDAO.updateAlreadyPay(paylog.getContractid());
        }
        return false;
    }

    @Override
    public List<Paylog> getAllPaylogsByContractId(Contract contract) throws Exception {
        return paylogDAO.getAllPaylogsByContractId(contract);
    }

    @Override
    public boolean delete(Paylog paylog) {
        paylog = paylogDAO.findXxByConditions("cost,paylogid,contractid", "paylogid='" + paylog.getPaylogid() + "'", Paylog.class);
        Contract contract = contractDAO.findXxByConditions("contractid,alreadyPay", "contractid='" + paylog.getContractid() + "'", Contract.class);
        contract.setAlreadyPay(contract.getAlreadyPay()-paylog.getCost());
        contractDAO.updateXxClomuns("alreadyPay",contract);
        return paylogDAO.deleteObj(paylog);
    }

    @Override
    public Paylog getVoById(Integer id) {
        return null;
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        return null;
    }
}
