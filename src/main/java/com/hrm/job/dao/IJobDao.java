package com.hrm.job.dao;

import com.hrm.commons.beans.Job;
import com.hrm.utils.PageModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IJobDao {

    @SelectProvider(type = jobProvider.class, method = "selectJob")
    List<Job> selectJob(@Param("name") String name, @Param("pageModel")PageModel pageModel);

    @SelectProvider(type = jobProvider.class, method = "selectJobCount")
    int selectJobCount(String name);

    @Select("select * from job_inf where id=#{id}")
    Job selectJobById(int id);

    @Update("update job_inf set name=#{name},remark=#{remark} where id=#{id}")
    int updateJob(Job job);

    @DeleteProvider(type = jobProvider.class,method = "deleteJob")
    int deleteJob(@Param("ids") Integer[] ids);

    @Insert("insert into job_inf (name,remark) value(#{name},#{remark})")
    int insertJob(Job job);
}
