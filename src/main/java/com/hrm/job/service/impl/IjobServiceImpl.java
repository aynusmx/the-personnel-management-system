package com.hrm.job.service.impl;

import com.hrm.commons.beans.Job;
import com.hrm.job.dao.IJobDao;
import com.hrm.job.service.IJobService;
import com.hrm.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IjobServiceImpl implements IJobService {
    @Autowired
    IJobDao iJobDao;
    @Override
    public List<Job> findJob(String name, PageModel pageModel) {
        return iJobDao.selectJob(name,pageModel);
    }

    @Override
    public int findJobCount(String name) {
        return iJobDao.selectJobCount(name);
    }

    @Override
    public Job findJobById(int id) {
        return iJobDao.selectJobById(id);
    }

    @Override
    public int modifyJob(Job job) {
        return iJobDao.updateJob(job);
    }

    @Override
    public int removeJob(Integer[] ids) {
        return iJobDao.deleteJob(ids);
    }

    @Override
    public int addJob(Job job) {
        return iJobDao.insertJob(job);
    }
}
