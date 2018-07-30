package lz.cm.dao;

import lz.cm.vo.Rcpaylog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IRcpaylogDAO extends IDAO<Integer, Rcpaylog> {

    Integer getRencaiAlreadyPay(Integer rencaiid);
}
