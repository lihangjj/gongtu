package lz.cm.dao;

import lz.cm.vo.Job;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IJobDAO extends IDAO<Integer,Job> {
    boolean delete(Job job);
}
