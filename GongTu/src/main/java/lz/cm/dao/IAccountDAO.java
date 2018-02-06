package lz.cm.dao;

import lz.cm.vo.Account;
import lz.cm.vo.Dept;
import lz.cm.vo.Job;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IAccountDAO extends IDAO<String,Account> {

}
