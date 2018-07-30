package lz.cm.service;

import lz.cm.vo.Shoupay;

import java.util.List;

public interface IShoupayService extends IService<Integer, Shoupay> {

    boolean add(List<Shoupay> shoupayList);

    boolean deleteShoupay(Shoupay shoupay);

    boolean editShoupay(Shoupay shoupay);

    Shoupay getShouPayInfo(Shoupay shoupay);
}
