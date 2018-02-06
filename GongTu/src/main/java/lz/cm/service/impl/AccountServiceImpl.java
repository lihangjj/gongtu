package lz.cm.service.impl;

import lz.cm.dao.IAccountDAO;
import lz.cm.service.IAccountService;
import lz.cm.vo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private IAccountDAO accountDAO;
    @Override
    public boolean edit(Account account) throws Exception {
        return accountDAO.doUpdate(account);
    }

    @Override
    public boolean add(Account account) throws Exception {
        return accountDAO.doCreate(account);
    }

    @Override
    public boolean delete(Account account) throws Exception {
        return accountDAO.doDelete(account);
    }

    @Override
    public Account getVoById(String id) {
        return null;
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        return accountDAO.splitVoByFlag(Account.class,column,keyWord,currentPage,lineSize,getCondition(parameterName,"=",parameterValue));
    }
}
