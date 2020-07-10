package com.hrm.employee.service.impl;

import com.hrm.commons.beans.Dept;
import com.hrm.commons.beans.Employee;
import com.hrm.commons.beans.Job;
import com.hrm.employee.dao.IEmployeeDao;
import com.hrm.employee.service.IEmployeeService;
import com.hrm.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IEmployeeServiceImpl implements IEmployeeService {

    @Autowired
    IEmployeeDao iEmployeeDao;
    @Override
    public List<Job> findAllJob() {
        return iEmployeeDao.selectAllJob();
    }

    @Override
    public List<Dept> findAllDept() {
        return iEmployeeDao.selectAllDept();
    }

    @Override
    public List<Employee> findEmployee(Employee employee, PageModel pageModel) {
        return iEmployeeDao.selectEmployee(employee,pageModel);
    }

    @Override
    public int findEmployeeCount(Employee employee) {
        return iEmployeeDao.selectEmployeeCount(employee);
    }

    @Override
    public Employee findEmployeeById(int id) {
        return iEmployeeDao.selectEmployeeById(id);
    }

    @Override
    public int modifyEmployee(Employee employee) {
        return iEmployeeDao.updateEmployee(employee);
    }

    @Override
    public int removeEmployee(Integer[] ids) {
        return iEmployeeDao.deleteEmployee(ids);
    }

    @Override
    public int addEmployee(Employee employee) {
        return iEmployeeDao.insertEmployee(employee);
    }




}
