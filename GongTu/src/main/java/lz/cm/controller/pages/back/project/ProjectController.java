package lz.cm.controller.pages.back.project;

import lz.cm.controller.AbstractControllerAdapter;
import lz.cm.service.IProjectService;
import lz.cm.vo.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/pages/back/project")
public class ProjectController extends AbstractControllerAdapter {
    @Autowired
    private IProjectService projectService;

    @ResponseBody
    @RequestMapping("delete")
    boolean delete(Job job) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @ResponseBody
    @RequestMapping("edit")
    boolean edit(Job job) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @RequestMapping("addPre")
    String addPre(Model model, HttpServletRequest request) {
        return "pages/back/job/job-addPre";
    }

    @RequestMapping("add")
    @ResponseBody
    boolean add(Job job) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @RequestMapping("list")
    String list(HttpServletRequest request, Model model) throws Exception {

        setColumnMap(request,"项目名称:name|金额:cost|执行人:executive");
        return "pages/back/project/project-list";
    }

    @RequestMapping("listAjax")
    @ResponseBody
    Object listAjax(HttpServletRequest request) throws Exception {
        return handSplit(request, projectService);
    }

    @ResponseBody
    @RequestMapping("plDeleteProject")
    boolean plDeleteContract(HttpServletRequest request) {
        try {
            String[] ids = request.getParameter("str").split("-");

            return projectService.plDeleteProject(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
