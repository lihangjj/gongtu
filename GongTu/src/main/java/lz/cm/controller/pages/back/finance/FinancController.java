package lz.cm.controller.pages.back.finance;

import lz.cm.controller.AbstractControllerAdapter;
import lz.cm.service.IAccountService;
import lz.cm.service.ICostService;
import lz.cm.service.IPaylogService;
import lz.cm.vo.*;
import lz.util.MathUtil;
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
    @Autowired
    private ICostService costService;

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

    @RequestMapping("accountList")
    String accountList(HttpServletRequest request, Model model) {
        model.addAllAttributes(handSplit(request, accountService));
        return "pages/back/finance/account-list";
    }

    @ResponseBody
    @RequestMapping("costListAjax")
    Object costListAjax(HttpServletRequest request) {

        return handSplit(request, costService);
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
        System.err.println(account);
        try {
            return accountService.add(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("checkAccount")
    @ResponseBody
    boolean checkAccount(Account account) {
        System.err.println(account);
        try {
            return accountService.getVoById(account.getAccountid()) == null;
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

    @RequestMapping("costAddPre")
    String costAddPre() {

        return "pages/back/finance/cost-addPre";
    }

    @RequestMapping("costAdd")
    String costAdd(Cost cost, Model model) {
        System.err.println(cost);


        if (cost.getFangzu() == null || "".equals(cost.getFangzu())) {
            cost.setFangzu(0.0);
        } else {
            cost.setFangzu(MathUtil.round(cost.getFangzu(), 2));
        }
        if (cost.getShui() == null || "".equals(cost.getShui())) {
            cost.setShui(0.0);
        } else {
            cost.setShui(MathUtil.round(cost.getShui(), 2));

        }
        if (cost.getDian() == null || "".equals(cost.getDian())) {
            cost.setDian(0.0);
        } else {
            cost.setDian(MathUtil.round(cost.getDian(), 2));
        }
        if (cost.getShebei() == null || "".equals(cost.getShebei())) {
            cost.setShebei(0.0);
        } else {
            cost.setShebei(MathUtil.round(cost.getShebei(), 2));


        }
        if (cost.getQiche() == null || "".equals(cost.getQiche())) {
            cost.setQiche(0.0);
        } else {
            cost.setQiche(MathUtil.round(cost.getQiche(), 2));


        }
        if (cost.getHaocai() == null || "".equals(cost.getHaocai())) {
            cost.setHaocai(0.0);
        } else {
            cost.setHaocai(MathUtil.round(cost.getHaocai(), 2));


        }
        if (cost.getTuiguang() == null || "".equals(cost.getTuiguang())) {
            cost.setTuiguang(0.0);
        } else {
            cost.setTuiguang(MathUtil.round(cost.getTuiguang(), 2));


        }
        if (cost.getCanyin() == null || "".equals(cost.getCanyin())) {
            cost.setCanyin(0.0);
        } else {
            cost.setCanyin(MathUtil.round(cost.getCanyin(), 2));
        }


        cost.setHeji(cost.getFangzu()
                + cost.getCanyin()
                + cost.getDian()
                + cost.getShebei()
                + cost.getShui()
                + cost.getHaocai()
                + cost.getTuiguang()
                + cost.getQiche());
        Double d = cost.getHeji();
        if (d.toString().contains(".")) {
            d = d.toString().split("\\.")[1].length() > 2 ? MathUtil.round(d, 2) : d;
            cost.setHeji(d);
        }


        if (cost.getDate() == null || "".equals(cost.getDate())) {
            cost.setDate(new Date());
        }


        try {
            if (costService.add(cost)) {
                setMsg("vo.add.success", "成本", true, model);

            } else {
                setMsg("vo.add.failure", "成本", false, model);
            }
            return "pages/back/finance/cost-addPre";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pages/back/finance/cost-addPre";

    }

    @RequestMapping("costList")
    String costList(HttpServletRequest request, Model model) throws Exception {

        setColumnMap(request,"总计:heji|房租:fangzu|水费:shui|电费:dian|汽车:qiche|餐饮:canyin|办公耗材:haocai|办公设备:shebei|网络推广:tuiguang");
        return "pages/back/finance/cost-list";
    }

    @RequestMapping("plDeleteAccount")
    @ResponseBody
    boolean plDeleteAccount(HttpServletRequest request) {

        String str = request.getParameter("str");
        try {
            return accountService.plDeleteAccount(str.split("-"));
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
