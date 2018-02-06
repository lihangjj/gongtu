package lz.cm.dao;

import lz.cm.vo.Contract;
import lz.cm.vo.Paylog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IPaylogDAO extends IDAO<Integer, Paylog> {
    List<Paylog> getAllPaylogsByContractId(Contract contract);
}
