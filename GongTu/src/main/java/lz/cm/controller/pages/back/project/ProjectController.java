package lz.cm.controller.pages.back.project;

import lz.cm.controller.AbstractControllerAdapter;
import lz.cm.service.IMemberServiceBack;
import lz.cm.service.IProjectService;
import lz.cm.vo.Job;
import lz.cm.vo.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequestMapping("/pages/back/project")
public class ProjectController extends AbstractControllerAdapter {
    @Autowired
    private IProjectService projectService;
    @Autowired
    private IMemberServiceBack memberServiceBack;

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

    @ResponseBody
    @RequestMapping("editProjectStatus")
    boolean editProjectStatus(Project project) {

        return projectService.editProjectStatus(project);
    }

    @RequestMapping("huizong")
    String huizong(Model model, HttpServletRequest request) {
        String status = request.getParameter("status");
        System.err.println(status);

        try {
            model.addAttribute("allMembers", memberServiceBack.getAllExecutive(status));
            model.addAttribute("status", status);
            if(status==null||"".equals(status)){
                model.addAttribute("status", "所有");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "pages/back/project/project-huizong";
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

        setColumnMap(request, "公司名称:companyName|项目名称:name|执行人:executive");
        return "pages/back/project/project-list";
    }

    @RequestMapping("listAjax")
    @ResponseBody
    Object listAjax(HttpServletRequest request) throws Exception {
        Map<String, Object> resMap;
        if (!hasAnyRoles(new String[]{"project:总经理-副总-财务主管", "project:人才主管-项目主管"})) {//不具备此角色，就移除不是自己的项目
            String parameterName = request.getParameter("parameterName");
            String parameterValue = request.getParameter("parameterValue");
            String keyWord = request.getParameter("keyWord");
            String column = request.getParameter("column");
            Integer currentPage = null;
            Integer lineSize = null;
            try {
                currentPage = Integer.parseInt(request.getParameter("currentPage"));
            } catch (NumberFormatException e) {
            }
            try {
                lineSize = Integer.parseInt(request.getParameter("lineSize"));
            } catch (NumberFormatException e) {

            }
            resMap = projectService.splitVoByExcutive(column, keyWord, currentPage, lineSize, parameterName, parameterValue, getName());

        } else {
            resMap = handSplit(request, projectService);
        }
        return resMap;
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

    //将人才添加到项目，需要增加使用单位
    @ResponseBody
    @RequestMapping("rencaisAddToProject")
    boolean rencaisAddToProject(HttpServletRequest request) {
        String[] ids = request.getParameter("str").split("-");

        Integer projectid = Integer.valueOf(request.getParameter("projectid"));
        try {
            return projectService.rencaisAddToProject(ids, projectid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    @ResponseBody
    @RequestMapping("rencaisFromProjectRemove")
    boolean rencaisFromProjectRemove(HttpServletRequest request) {
        String[] ids = request.getParameter("str").split("-");

        Integer projectid = Integer.valueOf(request.getParameter("projectid"));
        try {
            return projectService.rencaisFromProjectRemove(ids, projectid);
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
