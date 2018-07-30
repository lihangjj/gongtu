package lz.cm.service;

import lz.cm.vo.Cost;

public interface ICostService extends IService<Integer, Cost> {
    boolean edit(Cost cost) throws Exception;
    boolean add(Cost cost) throws Exception;
    boolean plDeleteCost(String[] ids)throws Exception;



}
