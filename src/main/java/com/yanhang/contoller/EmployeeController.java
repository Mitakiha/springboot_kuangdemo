package com.yanhang.contoller;

import com.yanhang.dao.DepartmentDao;
import com.yanhang.dao.EmployeeDao;
import com.yanhang.pojo.Department;
import com.yanhang.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 * @ Author     ：Yan
 * @ Date       ：Created in 16:00 2020/4/9
 * @ Description：
 * @ Modified By：
 * @Version: $
 */
@Controller
public class EmployeeController {
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;

    @RequestMapping("/emps")
    public String list(Model model) {
        Collection<Employee> employees = employeeDao.getAll();
        model.addAttribute("emps", employees);
        return "emp/list";
    }

    @GetMapping("/emp")
    public String toAddpage(Model model) {
        //查询所有部门的信息
        Collection<Department> departments = departmentDao.getDepartment();
        System.out.println(departments);
        model.addAttribute("departments", departments);
        return "emp/add";
    }

    @PostMapping("/emp")
    public String addEmp(Employee employee) {
        //添加操作
        System.out.println("save employee:" + employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    //去员工的修改页面
    @GetMapping("/emp/{id}")
    public String toUpdateEmp(@PathVariable("id") Integer id, Model model) {
        //查出原来的数据
        Employee employee = employeeDao.getEmployeeById(id);
        System.out.println(employee);
        model.addAttribute("emp", employee);
        //查询所有部门信息
        Collection<Department> departments = departmentDao.getDepartment();
        System.out.println(departments);
        model.addAttribute("departments", departments);
        return "emp/update";
    }

    @PostMapping("/updateEmp")
    public String updateEmp(Employee employee) {
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    @GetMapping("/delemp/{id}")
    public String toDeleteEmp(@PathVariable("id") Integer id) {
        employeeDao.delete(id);
        return "redirect:/emps";
    }

    @RequestMapping("/user/logout")
    public String UserLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/index.html";
    }
}
