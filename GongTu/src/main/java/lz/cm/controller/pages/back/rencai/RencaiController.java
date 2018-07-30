package lz.cm.controller.pages.back.rencai;

import lz.cm.controller.AbstractControllerAdapter;
import lz.cm.service.*;
import lz.cm.vo.*;
import lz.util.str.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;


@Controller
@RequestMapping("/pages/back/rencai")
public class RencaiController extends AbstractControllerAdapter {
    @Autowired
    private IProjectService projectService;
    @Autowired
    private IRencaiService rencaiService;
    @Autowired
    private ISydwService sydwService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IRcpaylogService rcpaylogService;
    @Autowired
    private IMemberServiceBack memberServiceBack;

    @ResponseBody
    @RequestMapping("plDeleteRencai")
    boolean plDeleteRencai(HttpServletRequest request) {
        try {
            String ids[] = request.getParameter("str").split("-");

            return rencaiService.plDeleteRencai(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @RequestMapping("addPre")
    String addPre(Model model, HttpServletRequest request) {
        model.addAttribute("allContract", rencaiService.getContractName());
        String res = request.getParameter("res");
        model.addAttribute("allMember", memberServiceBack.getAllMemberIdAndNames());
        if (res == null || "".equals(res)) {//来自菜单栏访问

        } else {//来自服务器内部跳转
            if (res.equals("f")) {
                setMsg("vo.add.failure", "人才", false, model);
            } else {
                setMsg("vo.add.success", "人才", true, model);
            }
        }


        return "pages/back/rencai/rencai-addPre";
    }

    @RequestMapping("editPre")
    String editPre(Model model, HttpServletRequest request, Rencai rencai) {
        model.addAttribute("rc", rencaiService.getVoById(rencai.getRencaiid()));
        model.addAttribute("allMember", memberServiceBack.getAllMemberIdAndNames());
        model.addAttribute("allContract", rencaiService.getContractName());
        String editRes = request.getParameter("editRes");

        if (editRes == null || "".equals(editRes)) {//来自菜单栏访问

        } else {//来自服务器内部跳转
            if (editRes.equals("f")) {
                setMsg("vo.edit.failure", "人才", false, model);
            } else {
                setMsg("vo.edit.success", "人才", true, model);
            }
        }

        return "pages/back/rencai/rencai-editPre";
    }

    @RequestMapping("edit")
    @Transactional(propagation = Propagation.REQUIRED)
    String edit(Model model, HttpServletRequest request, Rencai rencai, MultipartFile rcPhoto) {
        model.addAttribute("rc", rencaiService.getVoById(rencai.getRencaiid()));
        model.addAttribute("allContract", rencaiService.getContractName());
        Rencai oldRc = rencaiService.getVoById(rencai.getRencaiid());
        rencai.setDflag(0);
        if (rcPhoto.getSize() > 0) {//有新的照片上传,设置新照片
            rencai.setPhoto(createFileName(rcPhoto));
            rencai.setBigPhoto(createFileName(rcPhoto));
        } else {//保留原来的照片
            rencai.setBigPhoto(oldRc.getBigPhoto());
            rencai.setPhoto(oldRc.getPhoto());
        }

        List<MultipartFile> files = null;
        String hetong = "";
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
            files = mrequest.getFiles("hetongFile");
            if (files.get(0).getSize() > 0) {//如果有合同文件上传
                for (MultipartFile m : files) {
                    if (m.getSize() > 0) {//确实有合同上传
                        String file = createFileName(m);
                        hetong += file + ":";
                    }
                }
                rencai.setHetong(hetong);
            } else {//没有合同上传，设置原来的合同
                rencai.setHetong(oldRc.getHetong());
            }

        }
        String editRes = "";

        try {
            String flag = "";

            if (rencaiService.edit(rencai)) {
                if (rcPhoto.getSize() > 0) {//有新的照片上传,设置新照片
                    if (saveFile(rencai.getPhoto(), rencai.getBigPhoto(), 50, rcPhoto, request)) {//新的照片保存成功
                        deleteFile(oldRc.getPhoto(), oldRc.getBigPhoto(), request);//旧照片删除,不在乎成功或失败
                    } else {//新的照片保存失败
                        flag += "s";
                    }
                }
                if (files != null) {
                    if (files.get(0).getSize() > 0) {//如果有合同文件上传
                        //先将原来的合同删除
                        String oldHt[] = oldRc.getHetong().split(":");
                        for (String s : oldHt) {
                            //删除合同，无论成功或失败
                            deleteFile(s, request);
                        }

                        MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
                        files = mrequest.getFiles("hetongFile");
                        String ht[] = rencai.getHetong().split(":");
                        int y = 0;
                        int n = 0;
                        for (int x = 0; x < ht.length; x++) {
                            MultipartFile file = files.get(x);
                            if (file.getSize() > 0) {//确实有合同上传
                                n++;//合同文件数
                                String bigPhoto = createFileName(file);
                                String minphoto = ht[x];
                                if (saveFile(minphoto, bigPhoto, 300, file, request)) {//合同保存成功
                                    System.err.println("控制器外面删除" + deleteFile(bigPhoto, request));
                                    y++;
                                }
                            }
                        }
                        if (y != n) {
                            flag += "s";
                        }

                    }
                }
                //处理使用单位
                //1。删除原来的使用单位,不管成功与否
                rencaiService.deleteOldSydwByRcId(rencai.getRencaiid());
                Enumeration<String> enu = request.getParameterNames();
                while (enu.hasMoreElements()) {
                    String pName = enu.nextElement();
                    if (pName.equals("sydanwei")) {
                        String[] values = request.getParameterValues(pName);
                        Integer rencaiid = rencai.getRencaiid();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        System.out.println(values.length + "数组长度");
                        for (int x = 0; x < values.length; x++) {
                            if (!(values[x] == null || "".equals(values[x]))) {//单位名称不为空，表示有使用单位
                                Sydw sydw = new Sydw();
                                sydw.setSydanwei(values[x]);
                                sydw.setRencaiid(rencaiid);
                                String startTime = request.getParameterValues("startTime")[x];
                                String overTime = request.getParameterValues("overTime")[x];
                                String xmName = request.getParameterValues("xmName")[x];
                                if (startTime == null || "".equals(startTime)) {
                                    sydw.setStartTime(null);
                                } else {
                                    sydw.setStartTime(simpleDateFormat.parse(request.getParameterValues("startTime")[x]));
                                }
                                if (overTime == null || "".equals(overTime)) {
                                    sydw.setOverTime(null);
                                } else {
                                    sydw.setOverTime(simpleDateFormat.parse(request.getParameterValues("overTime")[x]));
                                }
                                sydw.setXmName(xmName);
                                sydw.setZzType(request.getParameterValues("zzType")[x]);
                                if (!sydwService.add(sydw)) {
                                    flag += "s";
                                }
                            }

                        }

                    }
                }


                if (flag.contains("s")) {//如果flag包含有了s了，说明中间有错
                    editRes = "f";

                } else {
                    editRes = "t";

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "redirect:/pages/back/rencai/editPre?editRes=" + editRes + "&rencaiid=" + rencai.getRencaiid();
    }

    @RequestMapping("getProjectsByContractId")
    @ResponseBody
    Object getProjectsByContractId(Contract contract) {


        try {
            return projectService.getProjectsByContractId(contract.getContractid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("checkIdCard")
    @ResponseBody
    boolean checkIdCard(HttpServletRequest request) {
        String id = request.getParameter("data");
        if (id.matches(StrUtil.idCardRegex)) {
            return true;

        } else {
            return false;
        }

    }

    @RequestMapping("checkIdCardAlreadyExceptCurrent")
    @ResponseBody
    String checkIdCardAlreadyExceptCurrent(HttpServletRequest request) {
        String id = request.getParameter("data");
        if (id.matches(StrUtil.idCardRegex)) {
            if (id.equals(request.getParameter("oldId"))) {
                return "true:";
            }
            if (rencaiService.checkIdCardAlready(id)) {
                return "true:";

            } else {
                return "false:该身份证已存在";
            }

        } else {
            return "false:请输入正确的身份证号码";
        }

    }


    @RequestMapping("checkIdCardAlready")
    @ResponseBody
    String checkIdCardAlready(HttpServletRequest request) {
        String id = request.getParameter("data");
        if (id.matches(StrUtil.idCardRegex)) {
            if (rencaiService.checkIdCardAlready(id)) {
                return "true:";

            } else {
                return "false:该身份证已存在";
            }

        } else {
            return "false:请输入正确的身份证号码";
        }

    }


    @RequestMapping("add")
    @Transactional(propagation = Propagation.REQUIRED)
    String add(Rencai rencai, HttpServletRequest request, MultipartFile rcPhoto, Model model) {

        //人才照片不是空
        rencai.setDflag(0);
        String hetong = "";
        List<MultipartFile> files = null;

        String photo = createFileName(rcPhoto);
        String bigphoto = createFileName(rcPhoto);

        rencai.setPhoto(photo);
        rencai.setBigPhoto(bigphoto);
        String res = "";


        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
            files = mrequest.getFiles("hetongFile");
            for (MultipartFile m : files) {
                if (m.getSize() > 0) {//确实有合同上传
                    String file = createFileName(m);
                    hetong += file + ":";
                }
            }
            rencai.setHetong(hetong);
        }
        try {
            String flag = "";

            if (rencaiService.add(rencai) != null) {
                if (!(setUploadPath() + "nophoto.png").equals(photo)) {//如果有照片上传
                    if (!saveFile(photo, bigphoto, 50, rcPhoto, request)) {//保存照片失败
                        flag += "s";
                    }
                }
                if (files.size() > 0) {
                    if (request instanceof MultipartHttpServletRequest) {
                        MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
                        files = mrequest.getFiles("hetongFile");
                        String ht[] = rencai.getHetong().split(":");
                        System.err.println(ht.length + "合同长");
                        System.err.println(files.size() + "文件长");
                        int y = 0;
                        int n = 0;
                        for (int x = 0; x < ht.length; x++) {
                            MultipartFile file = files.get(x);
                            if (file.getSize() > 0) {//确实有合同上传
                                n++;
                                String bigPhoto = createFileName(file);
                                String minphoto = ht[x];
                                if (saveFile(minphoto, bigPhoto, 300, file, request)) {
                                    y++;
                                }
                            }
                        }
                        if (y != n) {
                            flag += "s";

                        }
                    }
                }

            } else {
                flag += "s";

            }
            Enumeration<String> enu = request.getParameterNames();

            while (enu.hasMoreElements()) {
                String pName = enu.nextElement();
                if (pName.equals("sydanwei")) {
                    String[] values = request.getParameterValues(pName);
                    Integer rencaiid = rencai.getRencaiid();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    for (int x = 0; x < values.length; x++) {
                        if (!(values[x] == null || "".equals(values[x]))) {//单位名称不为空，表示有使用单位
                            Sydw sydw = new Sydw();

                            sydw.setSydanwei(values[x]);
                            sydw.setRencaiid(rencaiid);
                            String startTime = request.getParameterValues("startTime")[x];
                            String overTime = request.getParameterValues("overTime")[x];
                            String xmName = request.getParameterValues("xmName")[x];
                            if (startTime == null || "".equals(startTime)) {
                                sydw.setStartTime(null);
                            } else {
                                sydw.setStartTime(simpleDateFormat.parse(request.getParameterValues("startTime")[x]));
                            }
                            if (overTime == null || "".equals(overTime)) {
                                sydw.setOverTime(null);
                            } else {
                                sydw.setOverTime(simpleDateFormat.parse(request.getParameterValues("overTime")[x]));
                            }
                            sydw.setXmName(xmName);
                            sydw.setZzType(request.getParameterValues("zzType")[x]);
                            if (!sydwService.add(sydw)) {
                                flag += "s";
                            }
                        }

                    }

                }
            }
            if (flag.contains("s")) {//如果flag包含有了s了，说明中间有错
                res = "f";

            } else {
                res = "t";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "redirect:/pages/back/rencai/addPre?res=" + res;
    }


    @RequestMapping("list")
    String list(HttpServletRequest request, Model model) throws Exception {

        setColumnMap(request, "姓名:name|学历专业:xlzhuanye|学历等级:xldengji");
        List<Account> accountList = (List<Account>) handSplit(request, accountService).get("allVo");
        model.addAttribute("allAccount", accountList);
        if (accountList.size() > 0) {
            model.addAttribute("firstAccount", accountList.get(0).getAccountid());
            model.addAttribute("firstBank", accountList.get(0).getBank());
        }
        List<Project> allP = (List<Project>) handSplit(request, projectService).get("allVo");
        Iterator<Project> iter = allP.iterator();
        while (iter.hasNext()) {
            Project p = iter.next();
            if ("完结".equals(p.getContract().getStatus())) {
                iter.remove();
            }
        }
        model.addAttribute("allProject", allP);
        String pid = request.getParameter("projectid");
        if (!("".equals(pid) || pid == null)) {//列表是来自项目列表的，指定了项目
            model.addAttribute("pid", pid);
        }
        model.addAttribute("allContract", rencaiService.getContractName());


        return "pages/back/rencai/rencai-list";
    }

    @RequestMapping("showHetong")
    String showHetong(HttpServletRequest request) {
        String rcname = request.getParameter("name");
        String imgs = request.getParameter("imgs");
        String img[] = imgs.split(":");
        request.setAttribute("imgs", img);
        request.setAttribute("rcname", rcname);
        return "pages/back/rencai/show-hetong";
    }

    @RequestMapping("listAjax")
    @ResponseBody
    Object listAjax(HttpServletRequest request) throws Exception {
        return handSplit(request, rencaiService);
    }

    @RequestMapping("pay")
    @ResponseBody
    Object pay(HttpServletRequest request, Rcpaylog rcpaylog) throws Exception {
        rcpaylog.setTime(getDetailDate(request.getParameter("time")));
        return rcpaylogService.add(rcpaylog);
    }

    @RequestMapping("showPayLog")
    @ResponseBody
    Object showPayLog(HttpServletRequest request) throws Exception {
        return rcpaylogService.getRcpaylogByRencaiid(Integer.valueOf(request.getParameter("rencaiid")));
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
        return "/upload/rencai/";
    }

    @Override
    protected String setUrl() {//分页的路径
        return "/pages/back/rencai/list";
    }


}
