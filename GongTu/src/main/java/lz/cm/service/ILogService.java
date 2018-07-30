package lz.cm.service;

import lz.cm.vo.Log;

import java.util.Map;

public interface ILogService extends IService<Integer, Log> {
    boolean edit(Log log) throws Exception;

    boolean add(Log log) throws Exception;


    boolean plDeleteLog(String[] ids)throws Exception;
    Map<String, Object> splitByExrcutive(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue,String executive) throws Exception;




}
