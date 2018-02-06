package lz.cm.controller.pages.back.contract;

import lz.cm.controller.AbstractControllerAdapter;
import lz.cm.service.IAccountService;
import lz.cm.service.IContractService;
import lz.cm.service.IMemberServiceBack;
import lz.cm.vo.Contract;
import lz.cm.vo.Project;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


@Controller
@RequestMapping("/pages/back/contract")
public class ContractController extends AbstractControllerAdapter {

    @Autowired
    private IContractService contractService;
    @Autowired
    private IMemberServiceBack memberServiceBack;
    @Autowired
    private IAccountService accountService;


    @ResponseBody
    @RequestMapping("plDeleteContract")
    @RequiresPermissions("contract:delete")
    boolean plDeleteContract(HttpServletRequest request) {
        try {
            String[] ids = request.getParameter("str").split("-");

            return contractService.plDeleteContract(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("editPre")
    String editPre(Contract contract, Model model) {
        try {
            contract = contractService.getVoById(contract.getContractid());
            model.addAttribute(contract);
            model.addAttribute("qt", contractService.getTypeProjectMap(contract).get(contract.getContractid() + "-qt"));
            model.addAttribute("px", contractService.getTypeProjectMap(contract).get(contract.getContractid() + "-px"));
            model.addAttribute("dp", contractService.getTypeProjectMap(contract).get(contract.getContractid() + "-dp"));
            model.addAttribute("zz", contractService.getTypeProjectMap(contract).get(contract.getContractid() + "-zz"));
            model.addAttribute("gk", contractService.getTypeProjectMap(contract).get(contract.getContractid() + "-gk"));
            model.addAttribute("names", memberServiceBack.getAllNames());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pages/back/contract/contract-editPre";
    }

    @RequestMapping("edit")
    @ResponseBody
    boolean edit(Contract contract, Model model, HttpServletRequest request) {
        try {
            System.err.println(contract);
            Enumeration<String> enumeration = request.getParameterNames();
            List<Project> addList = new ArrayList<>();
            List<Project> editList = new ArrayList<>();
            while (enumeration.hasMoreElements()) {

                String parameterName = enumeration.nextElement();
                String type = parameterName.split("-")[0];

                if (parameterName.contains("-name")) {
                    String[] values = request.getParameterValues(parameterName);

                    if (parameterName.contains("-name-")) {//表示修改之前的项目
                        for (int x = 0; x < values.length; x++) {
                            if (!("".equals(values[x]) || values[x] == null)) {//有项目名称
                                Project p = new Project();
                                p.setProjectid(Integer.valueOf(parameterName.split("-")[3]));
                                p.setName(values[x]);
                                String cost = request.getParameterValues(parameterName.replace("name", "cost"))[x];
                                if ("".equals(cost) || cost == null) {
                                    p.setCost(0);
                                } else {
                                    p.setCost(Integer.valueOf(cost));
                                }
                                p.setExecutive(request.getParameterValues(parameterName.replace("name", "executive"))[x]);
                                editList.add(p);
                            }
                        }

                    } else {//表示有新的项目添加
                        for (int x = 0; x < values.length; x++) {
                            if (!("".equals(values[x]) || values[x] == null)) {//有项目名称
                                Project p = new Project();
                                p.setContractid(contract.getContractid());
                                p.setName(values[x]);
                                String cost = request.getParameterValues(type + "-cost")[x];
                                if ("".equals(cost) || cost == null) {
                                    p.setCost(0);
                                } else {
                                    p.setCost(Integer.valueOf(cost));
                                }
                                p.setExecutive(request.getParameterValues(type + "-executive")[x]);
                                p.setType(type);
                                addList.add(p);
                            }
                        }
                    }
                }
            }
            return contractService.edit(contract, editList, addList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @RequestMapping("addPre")
    String addPre(Model model, HttpServletRequest request) {


        return "pages/back/contract/contract-addPre";
    }

    @RequestMapping("add")
    String add(Contract contract, HttpServletRequest request, Model model) {
        try {
            Enumeration<String> enumeration = request.getParameterNames();
            List<Project> list = new ArrayList<>();
            while (enumeration.hasMoreElements()) {
                String parameterName = enumeration.nextElement();
                String type = parameterName.split("-")[0];
                if (parameterName.contains("-name")) {
                    String[] values = request.getParameterValues(parameterName);
                    for (int x = 0; x < values.length; x++) {
                        if (!("".equals(values[x]) || values[x] == null)) {//有项目名称
                            Project p = new Project();
                            p.setName(values[x]);
                            String cost = request.getParameterValues(type + "-cost")[x];
                            if ("".equals(cost) || cost == null) {
                                p.setCost(0);
                            } else {
                                p.setCost(Integer.valueOf(cost));
                            }
                            p.setExecutive(request.getParameterValues(type + "-executive")[x]);
                            p.setType(type);
                            list.add(p);
                        }
                    }
                }
            }

            if (contractService.add(contract, list)) {
                setMsg("vo.add.success", "合同", true, model);
            } else {
                setMsg("vo.add.failure", "合同", false, model);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pages/back/contract/contract-addPre";
    }


    @RequestMapping("list")
    String list(HttpServletRequest request, Model model) throws Exception {
        String columnData = "公司名称:companyName|联系人:contact|备注:note";
        setColumnMap(request, columnData);

        return "pages/back/contract/contract-list";
    }

    @RequestMapping("clist")
    String clist(HttpServletRequest request, Model model) throws Exception {
        String columnData = "公司名称:companyName|负责人:principal|总金额:allCost";
        setColumnMap(request, columnData);
        model.addAttribute("allAccount", handSplit(request, accountService).get("allVo"));
        return "pages/back/contract/contract-clist";
    }

    @RequestMapping("listAjax")
    @ResponseBody
    Object listAjax(HttpServletRequest request) throws Exception {
        return handSplit(request, contractService);
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
