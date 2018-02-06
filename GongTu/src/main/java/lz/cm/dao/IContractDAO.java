package lz.cm.dao;

import lz.cm.vo.Contract;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Mapper
public interface IContractDAO extends IDAO<Integer, Contract> {
    boolean updateAlreadyPay(Contract contract);
    Contract getAlreadyPay(Integer contractid);
    @Transactional()
    boolean plDeleteContract(Map<String,Object> map);

}
