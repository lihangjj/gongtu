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
import java.util.Date;
import java.util.Iterator;
import java.util.List;


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
            if (!log.getMemberid().equals(getMid())){
                return "false:对不起，您只能够修改自己的日志";
            }else {
                if (logService.edit(log)){
                    return "true:日志修改成功！";
                }else {
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

        List<Project> allP = (List<Project>) handSplit(request, projectService).get("allVo");
        Iterator<Project> iter = allP.iterator();
        while (iter.hasNext()) {
            Project p = iter.next();
            if ("完结".equals(p.getContract().getStatus())) {
                iter.remove();
            }
        }
        model.addAttribute("allProject", allP);
        return "pages/back/log/log-addPre";
    }

    @RequestMapping("add")
    @ResponseBody
    boolean add(Log log) {
        System.err.println(log);
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
        model.addAttribute("allMembers",memberServiceBack.getAllMemberIdAndNames());
        System.err.println(date);
        model.addAttribute("date", date);
        return "pages/back/log/log-list";
    }

    @RequestMapping("listAjax")
    @ResponseBody
    Object listAjax(HttpServletRequest request, Model model) throws Exception {

        return handSplit(request, logService);
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
