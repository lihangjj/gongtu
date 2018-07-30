package lz.cm.service;

import lz.cm.vo.Contract;
import lz.cm.vo.Rencai;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IRencaiService extends IService<Integer, Rencai> {

    boolean checkIdCardAlready(String idCard);

    @Transactional(propagation = Propagation.REQUIRED)
    Rencai add(Rencai rencai) throws Exception;

    List<Contract> getContractName();

    @Transactional(propagation = Propagation.REQUIRED)
    boolean edit(Rencai rencai) throws Exception;

    boolean deleteOldSydwByRcId(int rcid) throws Exception;


    boolean plDeleteRencai(String[] ids);
}
