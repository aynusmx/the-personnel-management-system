package com.hrm.employee.handler;

import com.hrm.commons.beans.Dept;
import com.hrm.commons.beans.Employee;
import com.hrm.commons.beans.Job;
import com.hrm.employee.service.IEmployeeService;
import com.hrm.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeHandler {

    @Autowired
    IEmployeeService iEmployeeService;
    @RequestMapping("/findEmployee")                                           //接收查询区表单传来的job_id,dept_id，和表单信息。
    public String findEmployee(@RequestParam(defaultValue = "1") int pageIndex, Integer dept_id,Integer job_id,Employee employee, Model model){
       if(job_id != null){
           Job job = new Job();
           job.setId(job_id);
           employee.setJob(job);
       }
       if (dept_id != null){
           Dept dept = new Dept();
           dept.setId(dept_id);
           employee.setDept(dept);
       }



        //职位下拉列表信息
        List<Job> jobs = iEmployeeService.findAllJob();

        //部门下拉列表信息
        List<Dept> depts = iEmployeeService.findAllDept();

        //查询记录数
        int recordCount = iEmployeeService.findEmployeeCount(employee);
        PageModel pageModel = new PageModel();
        pageModel.setPageIndex(pageIndex);
        pageModel.setPageSize(2);
        pageModel.setRocordCount(recordCount);
        //员工分页查询和搜索
        List<Employee> employees = iEmployeeService.findEmployee(employee,pageModel);

        model.addAttribute("jobs",jobs);
        model.addAttribute("depts",depts);
        model.addAttribute("employees",employees);
        model.addAttribute("pageModel",pageModel);
        //将之前查询区传来的employee封装到request域，用来在查询区表单进行信息回显
        model.addAttribute("employee",employee);
        return "/jsp/employee/employee.jsp";
    }

    @RequestMapping("/findEmployeeById")
    public String findEmployeeById(int id,Model model,int pageIndex){

        //jobs,depts为修改页面的下拉列表信息做准备
        List<Job> jobs = iEmployeeService.findAllJob();
        List<Dept> depts = iEmployeeService.findAllDept();
        Employee employee = iEmployeeService.findEmployeeById(id);
        model.addAttribute("employee",employee);
        model.addAttribute("jobs",jobs);
        model.addAttribute("depts",depts);
        model.addAttribute("pageIndex",pageIndex);
        return "/jsp/employee/showUpdateEmployee.jsp";
    }

    @RequestMapping("/modifyEmployee")
    @ResponseBody
    public String modifyEmployee(Employee employee){

        int rows = iEmployeeService.modifyEmployee(employee);
        if(rows>0){
            return "OK";
        }else {
            return "FAIL";
        }
    }

    @RequestMapping("/removeEmployee")
    @ResponseBody
    public String removeEmployee(Integer[] ids){
        int rows = iEmployeeService.removeEmployee(ids);
        if (rows==ids.length){
            return "OK";
        }else {
            return "FAIL";
        }
    }

    @RequestMapping("/toEmployee")
    public String toEmployee(Model model){
        List<Job> jobs = iEmployeeService.findAllJob();
        List<Dept> depts = iEmployeeService.findAllDept();
        //使用model而不使用session:将信息存入session域，当信息有修改变动时，存入session域中的信息无法及时更新，且查询时可能会出现信息冲突
        model.addAttribute("jobs",jobs);
        model.addAttribute("depts",depts);
        return "/jsp/employee/showAddEmployee.jsp";
    }

    @RequestMapping("/addEmployee")
    @ResponseBody
    public Object addEmployee(Employee employee){
        int rows = iEmployeeService.addEmployee(employee);
        if(rows>0){
            int recordCount = iEmployeeService.findEmployeeCount(null);
            PageModel pageModel = new PageModel();
            pageModel.setRocordCount(recordCount);
            pageModel.setPageSize(2);
            return pageModel.getTotalSize();
        }else {
            return "FAIL";
        }
        /**
         * 添加员工成功后跳转到最后一页：
         * 因为每次添加员工，添加的员工总是表最后一位，想要跳转到员工添加后新员工的位置
         * 就需要获取员工页面总数和一页显示的个数，从而得出最后一页是第几页。
         */

    }
}
