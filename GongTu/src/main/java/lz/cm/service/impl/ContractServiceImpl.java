package lz.cm.service.impl;

import lz.cm.dao.*;
import lz.cm.service.IContractService;
import lz.cm.vo.Contract;
import lz.cm.vo.Message;
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
    @Autowired
    private IMessageDAO messageDAO;
    @Autowired
    private IMemberDAO memberDAO;

    @Override
    public boolean edit(Contract contract, List<Project> addList) throws Exception {
        if (contractDAO.doUpdate(contract)) {//修改基础信息成功
            int y = 0;
            projectDAO.doDeleteProjectByContractId(contract.getContractid()); //删除原来的项目,不管成功与否
            Date date = new Date();
            for (Project p : addList) {//添加新的项目
                p.setStatus(contract.getStatus());
                p.setAddDate(date);
                if (projectDAO.doCreate(p)) {
                    y++;
                }
            }

            return y == addList.size();
        }
        return false;
    }

    @Override
    public boolean add(Contract contract, List<Project> allProject, String sender) throws Exception {
        if (contractDAO.doCreate(contract)) {
            Date date = new Date();
            int x = 0;
            Message message = new Message();
            message.setTitle("新增项目:"+contract.getCompanyName());
            message.setTime(date);
            message.setFlag(1);
            message.setSender(sender);

            Set<String> allAccepter = new HashSet<>();
            String note = "";

            for (Project p : allProject) {
                p.setAddDate(date);
                p.setContractid(contract.getContractid());
                p.setStatus(contract.getStatus());
                projectDAO.doCreate(p);
                allAccepter.add(memberDAO.getMemberidByName(p.getExecutive()));
                note +="【"+ (x + 1) + ":" + p.getName() + "; 执行人:" + p.getExecutive() + "】";
                x++;
            }
            message.setNote(note);
            messageDAO.doCreate(message);//新建消息
            messageDAO.updateFlag(String.valueOf(message.getMessageid()));//发送消息
            Map<String, Object> pMap = new HashMap<>();
            pMap.put("messageid", message.getMessageid());
            int y = 0;
            for (String s : allAccepter) {
                pMap.put("memberid", s);
                if (messageDAO.sendTo(pMap)) {
                    y++;
                }
            }
            if (y == allAccepter.size()) {
                return x == allProject.size();
            }

            return false;
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
    public boolean checkCompanyName(String companyName) throws Exception {
        return contractDAO.checkCompanyName(companyName) == null;
    }

    @Override
    public Contract getVoById(Integer id) {
        return contractDAO.findById(id, Contract.class);
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {

        Map<String, Object> resMap = contractDAO.splitVoByFlag(Contract.class, column, keyWord, currentPage, lineSize, " dflag=0 "+parameterValue);
        //这个是不分页用于查询全部合同款的
        Map<String, Object> resMap1 = contractDAO.splitVoByFlag(Contract.class, column, keyWord, null, null, " dflag=0 "+parameterValue);

        List<Contract> allContract = (List<Contract>) resMap.get("allVo");
        List<Contract> allContract1 = (List<Contract>) resMap1.get("allVo");
        Map<String, Object> typeProjectMap = new HashMap<>();
        //付款记录的Map
        Map<Integer, Object> paylogMap = new HashMap<>();
        int allC=0;
        int alreadyPay=0;
        for (Contract c : allContract1){
            allC+=c.getAllCost();
            alreadyPay+=c.getAlreadyPay();
        }
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
        resMap.put("allC", allC);
        resMap.put("noPay", allC-alreadyPay);
        return resMap;
    }
}
