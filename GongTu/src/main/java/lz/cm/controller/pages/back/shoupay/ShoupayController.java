package lz.cm.controller.pages.back.shoupay;

import lz.cm.controller.AbstractControllerAdapter;
import lz.cm.service.IAccountService;
import lz.cm.service.IShoupayService;
import lz.cm.vo.Shoupay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/pages/back/shoupay")
public class ShoupayController extends AbstractControllerAdapter {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IShoupayService shoupayService;

    @RequestMapping("addPre")
    String addPre(Shoupay shoupay, Model model, HttpServletRequest request) {
        model.addAttribute("allAccount", handSplit(request, accountService).get("allVo"));
        return "pages/back/shoupay/shoupay-addPre";
    }

    @RequestMapping("add")
    @ResponseBody
    boolean add(HttpServletRequest request, @RequestBody List<Shoupay> shoupayList) {
        return shoupayService.add(shoupayList);
    }

    @RequestMapping("list")
    String list(HttpServletRequest request, Model model) {
        model.addAttribute("allAccount", handSplit(request, accountService).get("allVo"));
        setColumnMap(request, "明细:mingxi|备注:note|收款金额:shou|付款金额:pay");
        return "pages/back/shoupay/shoupay-list";

    }

    @RequestMapping("deleteShoupay")
    @ResponseBody
    boolean deleteShoupay(Shoupay shoupay) {

        return shoupayService.deleteShoupay(shoupay);
    }
    @RequestMapping("getShouPayInfo")
    @ResponseBody
    Shoupay getShouPayInfo(Shoupay shoupay){
        return shoupayService.getShouPayInfo(shoupay);
    }

    @RequestMapping("editShoupay")
    @ResponseBody
    boolean editShoupay(Shoupay shoupay) {

        return shoupayService.editShoupay(shoupay);
    }

    @RequestMapping("listAjax")
    @ResponseBody
    Object listAjax(HttpServletRequest request) {
        return handSplit(request, shoupayService);
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
