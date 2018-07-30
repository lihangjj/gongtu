package lz.cm.service.impl;

import lz.cm.dao.IAccountDAO;
import lz.cm.dao.IShoupayDAO;
import lz.cm.service.IShoupayService;
import lz.cm.vo.Account;
import lz.cm.vo.Shoupay;
import lz.util.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ShoupayServiceImpl implements IShoupayService {
    @Autowired
    private IAccountDAO accountDAO;
    @Autowired
    private IShoupayDAO shoupayDAO;

    @Override
    public boolean add(List<Shoupay> shoupayList) {
        Account account = new Account();
        int x = 0;
        for (Shoupay s : shoupayList) {
            if (("".equals(s.getShou()) || s.getShou() == null) && (s.getPay() == null || "".equals(s.getPay()))) {//没有收入也没有支出，这次就不记录

            } else {
                if ("".equals(s.getShou()) || s.getShou() == null) {//收入为空则设置成0，因为后面要加或减
                    s.setShou(0.0);
                }
                if ("".equals(s.getPay()) || s.getPay() == null) {//支出为空则设置成0，因为后面要加或减
                    s.setPay(0.0);
                }
                if ("".equals(s.getDate()) || s.getDate() == null) {//没有日期则设置为当前日期
                    s.setDate(new Date());
                }

                double currentYue = accountDAO.findXxByConditions("yue", "accountid='" + s.getAccountid() + "'", Account.class).getYue();
                double shoupayYue = currentYue + s.getShou() - s.getPay();
                s.setYue(shoupayYue);
                try {
                    //增加收支记账
                    shoupayDAO.insertAll(s);
                    account.setAccountid(s.getAccountid());
                    account.setYue(shoupayYue);
                    //马上更新账户当前余额
                    accountDAO.updateXxClomuns("yue", account);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            x++;

        }
        return x == shoupayList.size();
    }

    @Override
    public boolean deleteShoupay(Shoupay shoupay) {
        shoupay = shoupayDAO.findXxByConditions("shoupayid,accountid,shou,pay", "shoupayid='" + shoupay.getShoupayid() + "'", Shoupay.class);
        Account account = new Account();
        account.setAccountid(shoupay.getAccountid());
        account = accountDAO.findXxByConditions("accountid,yue", "accountid='" + account.getAccountid() + "'", Account.class);
        Double accYue = account.getYue() - shoupay.getShou() + shoupay.getPay();
        account.setYue(accYue);
        if (accountDAO.updateXxClomuns("yue", account)) {
            return shoupayDAO.deleteObj(shoupay);
        }
        return false;
    }
        //修改明细种
    @Override
    public boolean editShoupay(Shoupay shoupay) {
        Shoupay oShoupay=(Shoupay) shoupayDAO.findVoByVo(shoupay);//查询到原来的
        Account account = new Account();//查询到原来的改变账户
        account.setAccountid(oShoupay.getAccountid());
        account = accountDAO.findXxByConditions("accountid,yue", "accountid='" + account.getAccountid() + "'", Account.class);
        Double accYue = account.getYue() - oShoupay.getShou() + oShoupay.getPay();
        account.setYue(accYue);
        accountDAO.updateXxClomuns("yue", account);//修改之前的账户的操作,变回没有动过
        //要操作现在的账户
        double currentYue = accountDAO.findXxByConditions("yue", "accountid='" + shoupay.getAccountid() + "'", Account.class).getYue();
        double shoupayYue = currentYue + shoupay.getShou() - shoupay.getPay();
        shoupay.setYue(shoupayYue);
        shoupayDAO.updateAll(shoupay);

        account.setAccountid(shoupay.getAccountid());
        account.setYue(shoupayYue);
        //马上更新账户当前余额
        return  accountDAO.updateXxClomuns("yue", account);
    }
    //修改之前
    @Override
    public Shoupay getShouPayInfo(Shoupay shoupay) {
        return (Shoupay) shoupayDAO.findVoByVo(shoupay);
    }

    @Override
    public Shoupay getVoById(Integer id) {
        return null;
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {

        Map<String, Object> resMap = shoupayDAO.splitVoByFlag(Shoupay.class, column, keyWord, currentPage, lineSize, parameterValue + " ORDER BY date DESC");
        Map<String, Object> zongshoupayMap = shoupayDAO.splitVoByColumns("shou,pay", Shoupay.class, column, keyWord, null, null, parameterValue);
        List<Shoupay> allShoupay = (List<Shoupay>) zongshoupayMap.get("allVo");


        double zongshou = 0;
        double zongpay = 0;
        for (Shoupay s : allShoupay) {
            zongshou += s.getShou();
            zongpay += s.getPay();

        }
        resMap.put("chaxunzongshou", MathUtil.round(zongshou, 2));
        resMap.put("chaxunzongpay", MathUtil.round(zongpay, 2));
        List<Shoupay> currentPageShoupay = (List<Shoupay>) resMap.get("allVo");
        zongshou = 0;
        zongpay = 0;
        for (Shoupay s : currentPageShoupay) {
            zongshou += s.getShou();
            zongpay += s.getPay();
            Account account = accountDAO.findXxByConditions("accountid,bank,name", " accountid='" + s.getAccountid() + "'", Account.class);
            s.setAccount(account);
        }
        resMap.put("currentPagezongshou", MathUtil.round(zongshou, 2));
        resMap.put("currentPagezongpay", MathUtil.round(zongpay, 2));

        return resMap;
    }
}
