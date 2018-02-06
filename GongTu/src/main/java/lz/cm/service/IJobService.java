package lz.cm.service;

import lz.cm.vo.Job;
import lz.cm.vo.Job;

import java.util.List;

public interface IJobService extends IService<Integer, Job> {
    boolean edit(Job job) throws Exception;

    boolean add(Job job) throws Exception;


    boolean delete(Job job)throws Exception;



}
