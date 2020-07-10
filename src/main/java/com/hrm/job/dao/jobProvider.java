package com.hrm.job.dao;

import com.hrm.utils.PageModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class jobProvider {

    public String selectJob(final Map map){
        //select * from job_inf where name like '%' #{name} '%' limit #{} #{}
        String sql = new SQL(){
            {
                this.SELECT("*");
                this.FROM("job_inf");
                if (map.get("name") != null && !"".equals(map.get("name"))){
                    this.WHERE("name like '%' #{name} '%'");
                }
                PageModel pageModel = (PageModel) map.get("pageModel");
                this.LIMIT("#{pageModel.firstLimitParam},#{pageModel.pageSize}");
            }
        }.toString();
        return sql;
    }

    public String selectJobCount(final String name){
        String sql = new SQL(){
            {
                this.SELECT("count(*)");
                this.FROM("job_inf");
                if (name != null && !"".equals(name)){
                    this.WHERE("name like '%' #{name} '%'");
                }
            }
        }.toString();
        return sql;
    }

    public String deleteJob(@Param("ids") Integer[] ids){

        StringBuffer sql = new StringBuffer();
        sql.append("delete from job_inf where id in (");
        for (int id:ids){
            sql.append(id + ",");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(")");
        return sql.toString();
    }
}
