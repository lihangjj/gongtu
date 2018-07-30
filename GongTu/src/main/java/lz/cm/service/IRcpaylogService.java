package lz.cm.service;

import lz.cm.vo.Rcpaylog;

import java.util.List;

public interface IRcpaylogService extends IService<Integer, Rcpaylog> {

    boolean add(Rcpaylog log) throws Exception;
    List<Rcpaylog> getRcpaylogByRencaiid(Integer rencaiid)throws Exception;
}
