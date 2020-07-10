package com.hrm.notice.handler;

import com.hrm.commons.beans.Notice;
import com.hrm.commons.beans.User;
import com.hrm.notice.service.INoticeService;
import com.hrm.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeHandler {
    @Autowired
    INoticeService iNoticeService;

    @RequestMapping("/findNotice")
    public String findNotice(@RequestParam(defaultValue = "1") int pageIndex, Notice notice, Model model){
        PageModel pageModel = new PageModel();
        pageModel.setPageIndex(pageIndex);
        List<Notice> notices = iNoticeService.findNotice(notice,pageModel);

        int recordCount = iNoticeService.findNoticeCount(notice);
        pageModel.setRocordCount(recordCount);

        model.addAttribute("notices",notices);
        model.addAttribute("pageModel",pageModel);
        model.addAttribute("notice",notice);
//        for (Notice n:notices){
//            System.out.println(n);
//        }
        return "/jsp/notice/notice.jsp";
    }

    @RequestMapping("/modifyNotice")
    public String modifyNotice(Notice notice,Model model,String flag,int pageIndex){
        if (flag==null){
            notice = iNoticeService.selectNoticeById(notice.getId());
            model.addAttribute("notice",notice);
            model.addAttribute("pageIndex",pageIndex);
            return "/jsp/notice/showUpdateNotice.jsp";
        }else {
            int rows = iNoticeService.modifyNotice(notice);
            if(rows>0){
                return "redirect:/notice/findNotice?pageIndex="+pageIndex;
            }else {
                model.addAttribute("fail","公告信息修改失败！");
                return "/jsp/fail.jsp";
            }

        }
    }

    @RequestMapping("/previewNotice")
    public String previewNotice(Model model,int id){
        Notice notice = iNoticeService.selectNoticeById(id);
        model.addAttribute("notice",notice);
        return "/jsp/notice/previewNotice.jsp";
    }

    @RequestMapping("/removeNotice")
    public String removeNotice(Model model,Integer[] ids){
        int rows = iNoticeService.removeNotice(ids);
        if (rows == ids.length){
            return "redirect:/notice/findNotice";
        }else {
            model.addAttribute("fail","公告信息删除失败！");
            return "/jsp/fail.jsp";
        }
    }

    @RequestMapping("/addNotice")
    public String addNotice(Notice notice, Model model, HttpSession session){
        //获取当前登录用户，也就是发布公告的用户
        User login_user = (User) session.getAttribute("login_user");
        notice.setUser(login_user);
        int rows = iNoticeService.addNotice(notice);

        if(rows>0){
            PageModel pageModel = new PageModel();
            int recordCount = iNoticeService.findNoticeCount(null);
            pageModel.setRocordCount(recordCount);
            int totalSize = pageModel.getTotalSize();
            return "redirect:/notice/findNotice?pageIndex="+totalSize;
        }else {
            model.addAttribute("fail","添加信息失败！");
            return "/jsp/fail.jsp";
        }
    }
}
