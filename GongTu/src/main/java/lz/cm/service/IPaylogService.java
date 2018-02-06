package lz.cm.service;

import lz.cm.vo.Contract;
import lz.cm.vo.Paylog;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IPaylogService extends IService<Integer, Paylog> {

    boolean edit(Paylog paylog) throws Exception;
    @Transactional(propagation = Propagation.REQUIRED)
    boolean add(Paylog paylog) throws Exception;

    List<Paylog> getAllPaylogsByContractId(Contract contract) throws Exception;

}
