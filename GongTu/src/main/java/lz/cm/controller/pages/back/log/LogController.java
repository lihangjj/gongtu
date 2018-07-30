package lz.cm.controller.pages.back.log;

import lz.cm.controller.AbstractControllerAdapter;
import lz.cm.service.ILogService;
import lz.cm.service.IMemberServiceBack;
import lz.cm.service.IProjectService;
import lz.cm.vo.Log;
import lz.cm.vo.Project;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/pages/back/log")
public class LogController extends AbstractControllerAdapter {
    @Autowired
    private IProjectService projectService;

    @Autowired
    private ILogService logService;
    @Autowired
    private IMemberServiceBack memberServiceBack;


    @RequestMapping("edit")
    @ResponseBody
    String edit(Log log) {
        try {
            if (!log.getMemberid().equals(getMid())) {
                return "false:对不起，您只能够修改自己的日志";
            } else {
                if (logService.edit(log)) {
                    return "true:日志修改成功！";
                } else {
                    return "false:日志修改失败！";

                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false:日志修改失败！";

    }


    @RequestMapping("addPre")
    String addPre(Model model, HttpServletRequest request) {


        List<Project> allP = projectService.getALLSimpleProject();
        model.addAttribute("allProject", allP);
        if (!hasAnyRoles(new String[]{"project:总经理-副总-财务主管", "project:人才主管-项目主管"})) {//不具备此角色，
            allP = projectService.getProjectsByExecutive(getName());
        }
        Iterator<Project> iter = allP.iterator();
        while (iter.hasNext()) {
            Project p = iter.next();
            if ("完结".equals(p.getContract().getStatus())) {
                iter.remove();
            }
        }
        model.addAttribute("myProject", allP);
        return "pages/back/log/log-addPre";
    }

    @RequestMapping("add")
    @ResponseBody
    boolean add(Log log) {
        log.setTime(new Date());
        log.setMemberid(getMid());
        try {
            return logService.add(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @RequestMapping("list")
    String list(HttpServletRequest request, Model model) throws Exception {
        setColumnMap(request, "内容:note|");
        String date = new SimpleDateFormat("yyyy-M").format(new Date());
        model.addAttribute("allMembers", memberServiceBack.getAllMemberIdAndNames());
        model.addAttribute("date", date);
        return "pages/back/log/log-list";
    }

    @RequestMapping("listAjax")
    @ResponseBody
    Object listAjax(HttpServletRequest request, Model model) throws Exception {
        Map<String, Object> resMap = null;
        if (!hasAnyRoles(new String[]{"project:总经理-副总-财务主管", "project:人才主管-项目主管"})) {//不具备此角色，
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
            resMap = logService.splitByExrcutive(column, keyWord, currentPage, lineSize, parameterName, parameterValue, getMid());
        } else {
            resMap = handSplit(request, logService);
        }
        return resMap;
    }

    @ResponseBody
    @RequestMapping("plDeleteLog")
    @RequiresPermissions("log:delete")
    boolean plDeleteLog(HttpServletRequest request) {
        try {
            String[] ids = request.getParameter("str").split("-");
            return logService.plDeleteLog(ids);
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
