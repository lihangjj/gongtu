package lz.cm.controller.pages.back.message;

import lz.cm.controller.AbstractControllerAdapter;
import lz.cm.service.IMemberServiceBack;
import lz.cm.service.IMessageService;
import lz.cm.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;


@Controller
@RequestMapping("/pages/back/message")
public class MessageController extends AbstractControllerAdapter {
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IMemberServiceBack memberServiceBack;


    @RequestMapping("addPre")
    String addPre(Model model, HttpServletRequest request) {

        model.addAttribute("allMember", memberServiceBack.getAllMemberIdAndNames());
        return "pages/back/message/message-addPre";
    }

    @RequestMapping("add")
    @ResponseBody
    boolean add(Message message) {//只是先保存消息
        try {
            message.setTime(new Date());
            message.setSender(getName());
            return messageService.add(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("addAndSend")
    @ResponseBody
    boolean addAndSend(Message message, HttpServletRequest request) {
        String str = request.getParameter("str");
        message.setTime(new Date());
        message.setSender(getName());

        try {
            if (messageService.add(message)) {
                String ids[] = str.split("-");
                return messageService.sendTo(message.getMessageid(), ids);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("editAndSend")
    @ResponseBody
    boolean editAndSend(Message message, HttpServletRequest request) {
        Message omessage = messageService.getVoById(message.getMessageid());
        if (omessage.getFlag() == 1) {
            return false;
        }
        message.setTime(new Date());
        String str = request.getParameter("str");

        try {
            if (messageService.edit(message)) {//修改成功
                String ids[] = str.split("-");
                return messageService.sendTo(message.getMessageid(), ids);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("edit")
    @ResponseBody
    boolean edit(Message message, HttpServletRequest request) throws Exception {
        Message omessage = messageService.getVoById(message.getMessageid());
        if (omessage.getFlag() == 1) {
            return false;
        }
        message.setTime(new Date());
        try {
            return messageService.edit(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //我的消息列表
    @RequestMapping("mlist")
    String mlist(HttpServletRequest request, Model model) throws Exception {
        setColumnMap(request, "标题:title|内容:note");
        return "pages/back/message/message-mlist";
    }
    //收到消息列表
    @RequestMapping("list")
    String list(HttpServletRequest request, Model model) throws Exception {
        setColumnMap(request, "标题:title|内容:note");
        return "pages/back/message/message-list";
    }

    @RequestMapping("mListAjax")
    @ResponseBody
    Object mListAjax(HttpServletRequest request) throws Exception {

        return handSplit(request, messageService);
    }

    @RequestMapping("listAjax")
    @ResponseBody
    Object listAjax(HttpServletRequest request) throws Exception {
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
        Map<String, Object> map = messageService.shoudaoMessage(column, keyWord, currentPage, lineSize, parameterName, parameterValue, getMid());
        int allRecorders = (int) map.get("allRecorders");
        if (lineSize != null) {
            int pageSize = (int) Math.ceil(allRecorders / lineSize);
            request.setAttribute("pageSize", pageSize);
        }
        return map;

    }

    @RequestMapping("editPre")
    String editPre(HttpServletRequest request, Model model) throws Exception {
        String messageid = request.getParameter("messageid");
        Message message = messageService.getVoById(Integer.valueOf(messageid));
        if (message.getFlag() == 1) {
            throw new Exception("已发送的消息不能修改！");
        }
        model.addAttribute(message);
        model.addAttribute("allMember", memberServiceBack.getAllMemberIdAndNames());
        return "pages/back/message/message-editPre";
    }

    @ResponseBody
    @RequestMapping("plDeleteMessage")
    boolean plDeleteMessage(HttpServletRequest request) {
        String[] ids = request.getParameter("str").split("-");
        try {
            return messageService.plljDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @ResponseBody
    @RequestMapping("plDeleteGrMessage")
//批量删除个人收到的消息
    boolean plDeleteGrMessage(HttpServletRequest request) {
        String[] ids = request.getParameter("str").split("-");
        try {
            return messageService.plDeleteGr(ids, getMid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @ResponseBody
    @RequestMapping("plYdMessage")
//批量删除个人收到的消息
    boolean plYdMessage(HttpServletRequest request) {
        String[] ids = request.getParameter("str").split("-");
        System.err.println(ids);
        try {
            return messageService.plYdMessage(ids, getMid());
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
