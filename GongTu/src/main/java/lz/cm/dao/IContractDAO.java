package lz.cm.dao;

import lz.cm.vo.Contract;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Mapper
public interface IContractDAO extends IDAO<Integer, Contract> {
    boolean updateAlreadyPay(Integer contractid);

    Contract getAlreadyPay(Integer contractid);

    @Transactional()
    boolean plDeleteContract(Map<String, Object> map);

    List<Contract> getContractName();

    Integer checkCompanyName(String companyName);

}
