package lz.cm.service.impl;

import lz.cm.dao.IContractDAO;
import lz.cm.dao.IPaylogDAO;
import lz.cm.dao.IProjectDAO;
import lz.cm.service.IContractService;
import lz.cm.vo.Contract;
import lz.cm.vo.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContractServiceImpl implements IContractService {
    @Autowired
    private IContractDAO contractDAO;
    @Autowired
    private IProjectDAO projectDAO;
    @Autowired
    private IPaylogDAO paylogDAO;

    @Override
    public boolean edit(Contract contract, List<Project> editList, List<Project> addList) throws Exception {
        if (contractDAO.doUpdate(contract)) {//修改基础信息成功
            int x = 0;
            int y = 0;
            for (Project p : editList) {//修改原有项目
                if (projectDAO.doUpdate(p)) {
                    x++;
                }
            }
            Date date = new Date();
            for (Project p : addList) {//添加新的项目
                p.setAddDate(date);
                if (projectDAO.doCreate(p)) {
                    y++;
                }
            }
            return x == editList.size() && y == addList.size();
        }
        return false;
    }

    @Override
    public boolean add(Contract contract, List<Project> allProject) throws Exception {
        if (contractDAO.doCreate(contract)) {
            Date date = new Date();
            int x = 0;
            for (Project p : allProject) {
                p.setAddDate(date);
                p.setContractid(contract.getContractid());
                projectDAO.doCreate(p);
                x++;
            }
            return x == allProject.size();
        }

        return false;
    }

    @Override
    public Map<String, Object> getTypeProjectMap(Contract contract) throws Exception {
        Map<String, Object> typeProjectMap = new HashMap<>();
        List<Project> allP = projectDAO.getAllProjectByConstractId(contract.getContractid());
        List<Project> qt = new ArrayList<>();
        List<Project> px = new ArrayList<>();
        List<Project> zz = new ArrayList<>();
        List<Project> dp = new ArrayList<>();
        List<Project> gk = new ArrayList<>();
        for (Project p : allP) {
            switch (p.getType()) {
                case "qt":
                    qt.add(p);
                    break;
                case "px":
                    px.add(p);
                    break;
                case "gk":
                    gk.add(p);
                    break;
                case "zz":
                    zz.add(p);
                    break;
                case "dp":
                    dp.add(p);
                    break;
            }
        }
        typeProjectMap.put(contract.getContractid() + "-qt", qt);
        typeProjectMap.put(contract.getContractid() + "-dp", dp);
        typeProjectMap.put(contract.getContractid() + "-zz", zz);
        typeProjectMap.put(contract.getContractid() + "-px", px);
        typeProjectMap.put(contract.getContractid() + "-gk", gk);
        return typeProjectMap;
    }

    @Override
    public boolean plDeleteContract(String[] ids) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("ids", ids);
        projectDAO.plDeleteProjectByContractId(parameterMap);

        contractDAO.plDeleteContract(parameterMap);
        //只要删除合同集合，项目可能没有，就会是false
        return contractDAO.plDeleteContract(parameterMap);
    }

    @Override
    public Contract getVoById(Integer id) {
        return contractDAO.findById(id, Contract.class);
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        String condition;
        String s = getCondition(parameterName, "=", parameterValue);
        System.err.println(s);
        if ("".equals(s)) {
            condition = " dflag=0";
        } else {
            condition = s + " AND dflag=0";
        }

        Map<String, Object> resMap = contractDAO.splitVoByFlag(Contract.class, column, keyWord, currentPage, lineSize, condition);

        List<Contract> allContract = (List<Contract>) resMap.get("allVo");
        Map<String, Object> typeProjectMap = new HashMap<>();
        //付款记录的Map
        Map<Integer, Object> paylogMap = new HashMap<>();

        for (Contract c : allContract) {
            paylogMap.put(c.getContractid(), paylogDAO.getAllPaylogsByContractId(c));
            List<Project> allP = projectDAO.getAllProjectByConstractId(c.getContractid());
            List<Project> qt = new ArrayList<>();
            List<Project> px = new ArrayList<>();
            List<Project> zz = new ArrayList<>();
            List<Project> dp = new ArrayList<>();
            List<Project> gk = new ArrayList<>();
            for (Project p : allP) {
                switch (p.getType()) {
                    case "qt":
                        qt.add(p);
                        break;
                    case "px":
                        px.add(p);
                        break;
                    case "gk":
                        gk.add(p);
                        break;
                    case "zz":
                        zz.add(p);
                        break;
                    case "dp":
                        dp.add(p);
                        break;

                }

            }
            typeProjectMap.put(c.getContractid() + "-qt", qt);
            typeProjectMap.put(c.getContractid() + "-dp", dp);
            typeProjectMap.put(c.getContractid() + "-zz", zz);
            typeProjectMap.put(c.getContractid() + "-px", px);
            typeProjectMap.put(c.getContractid() + "-gk", gk);

        }
        resMap.put("typeProjectMap", typeProjectMap);
        resMap.put("paylogMap", paylogMap);
        return resMap;
    }
}
