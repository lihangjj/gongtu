package lz.cm.controller.pages.back.log;

import lz.cm.controller.AbstractControllerAdapter;
import lz.cm.service.ILogService;
import lz.cm.service.IProjectService;
import lz.cm.vo.Job;
import lz.cm.vo.Log;
import lz.cm.vo.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
        try {
            return logService.add(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @RequestMapping("list")
    String list(HttpServletRequest request, Model model) throws Exception {
        return "pages/back/job/job-list";
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
