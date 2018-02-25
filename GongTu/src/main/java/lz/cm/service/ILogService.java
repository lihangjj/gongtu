package lz.cm.service;

import lz.cm.vo.Log;

public interface ILogService extends IService<Integer, Log> {
    boolean edit(Log log) throws Exception;

    boolean add(Log log) throws Exception;


    boolean plDeleteLog(String[] ids)throws Exception;



}
