package lz.cm.controller.pages.back.finance;

import lz.cm.controller.AbstractControllerAdapter;
import lz.cm.dao.IAccountDAO;
import lz.cm.service.IAccountService;
import lz.cm.service.IDeptService;
import lz.cm.service.IJobService;
import lz.cm.service.IPaylogService;
import lz.cm.vo.Account;
import lz.cm.vo.Contract;
import lz.cm.vo.Job;
import lz.cm.vo.Paylog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/pages/back/finance")
public class FinancController extends AbstractControllerAdapter {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    @Autowired
    private IAccountService accountService;
    @Autowired
    private IPaylogService paylogService;

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


    @RequestMapping("addAccount")
    @ResponseBody
    boolean add(Account account) {
        try {
            return accountService.add(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("addApylog")
    @ResponseBody
    boolean addApyLog(Paylog paylog) {
        System.err.println(paylog);
        try {
            return paylogService.add(paylog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("getPaylogsByContractId")
    @ResponseBody
    List<Paylog> getPaylogsByContractId(Contract contract) {

        try {
            return paylogService.getAllPaylogsByContractId(contract);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
