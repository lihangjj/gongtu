package lz.cm.service;

import lz.cm.vo.Contract;
import lz.cm.vo.Project;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface IContractService extends IService<Integer, Contract> {
    @Transactional(propagation = Propagation.REQUIRED)
    boolean edit(Contract contract, List<Project> editList, List<Project> addList) throws Exception;

    @Transactional(propagation = Propagation.REQUIRED)
    boolean add(Contract contract, List<Project> allProject) throws Exception;
    //表单回填前的方法
    Map<String, Object> getTypeProjectMap(Contract contract) throws Exception;
    @Transactional(propagation = Propagation.REQUIRED)
    boolean plDeleteContract(String[] ids) throws Exception;

}
