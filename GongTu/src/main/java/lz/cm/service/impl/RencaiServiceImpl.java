package lz.cm.service.impl;

import lz.cm.dao.*;
import lz.cm.service.IRencaiService;
import lz.cm.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RencaiServiceImpl implements IRencaiService {
    @Autowired
    private IRencaiDAO rencaiDAO;
    @Autowired
    private ISydwDAO sydwDAO;
    @Autowired
    private IRcpaylogDAO rcpaylogDAO;
    @Autowired
    private IContractDAO contractDAO;
    @Autowired
    private IProjectDAO projectDAO;

    @Override
    public boolean checkIdCardAlready(String idCard) {
        return rencaiDAO.checkIdCardAlready(idCard) == null;
    }

    @Override
    public Rencai add(Rencai rencai) throws Exception {

        if (rencaiDAO.insertAll(rencai)) {
            rencai.setRencaiid(rencaiDAO.getLastInsertId());
            return rencai;
        }
        return null;
    }

    @Override
    public List<Contract> getContractName() {
        return contractDAO.getContractName();
    }

    @Override
    public boolean edit(Rencai rencai) throws Exception {
        return rencaiDAO.updateAll(rencai);
    }

    @Override
    public boolean deleteOldSydwByRcId(int rcid) throws Exception {
        return rencaiDAO.deleteOldSydw(rcid);
    }

    @Override
    public boolean plDeleteRencai(String[] ids) {
        Map<String, Object> pMap = new HashMap<>();
        pMap.put("ids", ids);
        return rencaiDAO.plDeleteRencai(pMap);
    }


    @Override
    public Rencai getVoById(Integer id) {
        Rencai rc = rencaiDAO.findById(id, Rencai.class);

        List<Sydw> sydwList = (List<Sydw>) sydwDAO.splitVoByFlag(Sydw.class, null, null, null, null, " rencaiid=" + id.toString()).get("allVo");
        for (Sydw s : sydwList) {
            s.setProjects((List<Project>) projectDAO.splitVoByFlag(Project.class, null, null,
                    null, null, " contractid in (select contractid from contract where companyName='" + s.getSydanwei() + "')").get("allVo"));
        }
        rc.setSydw(sydwList);
        return rc;
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        Map<String, Object> resMap = rencaiDAO.splitVoByColumns("rencaiid,qyren,rcType,name,zszhuanye,zsdengji,xlzhuanye,xldengji,photo,bigPhoto,dqTime,qyPrice,alreadyPay,hetong",Rencai.class, column, keyWord, currentPage, lineSize, parameterValue);
        List<Rencai> allRencai = (List<Rencai>) resMap.get("allVo");
        for (Rencai r : allRencai) {
            r.setSydw(rencaiDAO.getSydw(r.getRencaiid()));
            List<Rcpaylog> rcpaylogs = (List<Rcpaylog>) rcpaylogDAO.splitVoByFlag(Rcpaylog.class, null, null, null, null, " rencaiid=" + r.getRencaiid()).get("allVo");
            r.setRcpaylogList(rcpaylogs);
        }
        resMap.put("allVo", allRencai);
        return resMap;
    }
}
