package lz.cm.dao;

import lz.cm.vo.Account;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface IAccountDAO extends IDAO<String, Account> {
    boolean plDeleteAccount(Map<String, Object>map);

    Double getAllCountMoney();
}
