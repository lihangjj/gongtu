package lz.cm.service;

import lz.cm.vo.Account;

public interface IAccountService extends IService<String, Account> {

    boolean edit(Account account) throws Exception;

    boolean add(Account account) throws Exception;

    boolean delete(Account account) throws Exception;

    boolean plDeleteAccount(String[] ids) throws Exception;

    Double getAllCountMoney();

    boolean editYue(Account account) throws Exception;
}
