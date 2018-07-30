package lz.cm.service;

import lz.cm.vo.Sydw;

public interface ISydwService extends IService<Integer, Sydw> {

        boolean add(Sydw sydw)throws Exception;
        boolean edit(Sydw sydw)throws Exception;

}
