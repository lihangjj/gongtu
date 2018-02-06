package lz.cm.controller.pages.back.job;

import lz.cm.controller.AbstractControllerAdapter;
import lz.cm.service.IDeptService;
import lz.cm.service.IJobService;
import lz.cm.vo.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/pages/back/job")
public class JobController extends AbstractControllerAdapter {
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IJobService jobService;

    @ResponseBody
    @RequestMapping("delete")
    boolean delete(Job job) {
        try {
           return jobService.delete(job);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @ResponseBody
    @RequestMapping("edit")
    boolean edit(Job job) {
        try {

            return jobService.edit(job);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @RequestMapping("addPre")
    String addPre(Model model, HttpServletRequest request) {
        model.addAllAttributes(handSplit(request, deptService));
        return "pages/back/job/job-addPre";
    }

    @RequestMapping("add")
    @ResponseBody
    boolean add(Job job) {
        try {
            return jobService.add(job);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @RequestMapping("list")
    String list(HttpServletRequest request, Model model) throws Exception {
        model.addAllAttributes(handSplit(request, jobService));
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
