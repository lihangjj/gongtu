package lz.cm.dao;

import lz.cm.vo.Rencai;
import lz.cm.vo.Sydw;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IRencaiDAO extends IDAO<Integer, Rencai> {
    String checkIdCardAlready(String idCard);

    List<Sydw> getSydw(Integer rencaiid);

    boolean editAlreadyPay(Integer rencaiid);

    Integer getRencaiAlreadyPay(Integer rencaiid);

    boolean deleteOldSydw(Integer rencaiid);

    boolean plDeleteRencai(Map<String, Object> pMap);
}
