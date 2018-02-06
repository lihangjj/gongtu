package lz.cm.controller.pages.back.dept;

import lz.cm.controller.AbstractControllerAdapter;
import lz.cm.service.IDeptService;
import lz.cm.vo.Dept;
import lz.cm.vo.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/pages/back/dept")
public class DeptController extends AbstractControllerAdapter {

    @Autowired
    private IDeptService deptService;

    @ResponseBody
    @RequestMapping("checkDname")
    boolean checkDname(Dept dept) {
        try {
            return deptService.getDeptByDname(dept) == null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @ResponseBody
    @RequestMapping("getJobsByDeptId")
    List<Job> getJobsByDeptId(Dept dept) {
        try {
            return deptService.getJobsByDeptId(dept.getDeptid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("delete")
    boolean delete(Dept dept) {
        try {
            return deptService.delete(dept);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @ResponseBody
    @RequestMapping("edit")
    boolean edit(Dept dept) {
        try {
            return deptService.edit(dept);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @RequestMapping("addPre")
    String addPre() {
        return "pages/back/dept/dept_addPre";
    }

    @RequestMapping("add")
    @ResponseBody
    boolean add(Dept dept) {
        try {
            return deptService.add(dept);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @RequestMapping("list")
    String list(HttpServletRequest request, Model model) throws Exception {

        model.addAllAttributes(handSplit(request, deptService));

        return "pages/back/dept/dept-list";
    }

    @Override
    protected String setUploadPath() {
        return "/upload/cost/";
    }

    @Override
    protected String setUrl() {//分页的路径
        return "/pages/back/cost/list";
    }


}
