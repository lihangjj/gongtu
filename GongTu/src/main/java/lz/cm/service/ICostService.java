package lz.cm.service;

import lz.cm.vo.Cost;

public interface ICostService extends IService<Integer, Cost> {
    boolean edit(Cost cost) throws Exception;
    boolean add(Cost cost) throws Exception;
    boolean plDeleteLog(String[] ids)throws Exception;



}
