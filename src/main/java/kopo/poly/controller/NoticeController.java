package kopo.poly.controller;


import kopo.poly.dto.NoticeDTO;
import kopo.poly.service.INoticeService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping(value="/notice")
@Controller
public class NoticeController {


    @Resource(name = "NoticeService")
    private INoticeService noticeService;

    @GetMapping(value = "NoticeList")
    public String noticeList(ModelMap model) {
        log.info(this.getClass().getName() + ".noticeList start!");

        List<NoticeDTO> rList = noticeService.getNoticeList();

        if (rList == null) {
            rList = new ArrayList<NoticeDTO>();
        }

        model.addAttribute("rList", rList);

        rList = null;

        log.info(this.getClass().getName() + ".noticeList end!");

        return "/notice/NoticeList";
    }

    @GetMapping(value = "NoticeInfo")
    public String noticeInfo(HttpServletRequest request, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".noticeInfo Start!");

        String nSeq = CmmUtil.nvl(request.getParameter("nSeq"));

        log.info("nSeq : " + nSeq);

        NoticeDTO pDTO = new NoticeDTO();
        pDTO.setNoticeSeq(Long.parseLong(nSeq));

        NoticeDTO rDTO = noticeService.getNoticeInfo(pDTO, true);

        if (rDTO == null) {
            rDTO = new NoticeDTO();
        }
        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".noticeInfo End!");

        return "/notice/NoticeInfo";
    }

    @GetMapping(value = "NoticeEditInfo")
    public String noticeEditInfo(HttpServletRequest request, ModelMap model){
        log.info(this.getClass().getName() + ".noticeEditInfo Start!");

        String msg = "";

        try{
            String nSeq = CmmUtil.nvl(request.getParameter("nSeq"));
            log.info("nSeq : " + nSeq);
            NoticeDTO pDTO = new NoticeDTO();
            pDTO.setNoticeSeq(Long.parseLong(nSeq));
            NoticeDTO rDTO = noticeService.getNoticeInfo(pDTO, false);

            if (rDTO == null) {
                rDTO = new NoticeDTO();
            }

            model.addAttribute("rDTO", rDTO);
        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            log.info(this.getClass().getName() + ".NoticeUpdate end!");

            model.addAttribute("msg", msg);
        }
        log.info(this.getClass().getName() + ".noticeEditInfo End!");

        return "/notice/NoticeEditInfo";
    }

    @PostMapping(value = "NoticeUpdate")
    public String NoticeUpdate(HttpSession session, HttpServletRequest request, ModelMap model) {

        log.info(this.getClass().getName() + ".noticeUpdate Start!");

        String msg = "";

        try {
            String user_id = CmmUtil.nvl((String) session.getAttribute("SESSION_USER_ID"));
            String nSeq = CmmUtil.nvl((String) request.getParameter("nSeq"));
            String title = CmmUtil.nvl((String) request.getParameter("title"));
            String noticeYn = CmmUtil.nvl((String) request.getParameter("noticeYn"));
            String contents = CmmUtil.nvl((String) request.getParameter("contents"));

            log.info("user_id :"+user_id);
            log.info("nSeq :"+nSeq);
            log.info("title :"+title);
            log.info("noticeYn :"+noticeYn);
            log.info("contents :"+contents);

            NoticeDTO pDTO = new NoticeDTO();

            pDTO.setUserId(user_id);
            pDTO.setNoticeSeq(Long.parseLong(nSeq));
            pDTO.setTitle(title);
            pDTO.setNoticeYn(noticeYn);
            pDTO.setContents(contents);

            noticeService.updateNoticeInfo(pDTO);

            msg="수정되었습니다.";


        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            log.info(this.getClass().getName() + ".noticeUpdate End!");

            model.addAttribute("msg", msg);

        }

        return "/notice/MsgToList";
    }


    @GetMapping(value = "NoticeDelete")
    public String noticeDelete(HttpServletRequest request, ModelMap model) {
        log.info(this.getClass().getName() + ".noticeDelte Start");

        String msg = "";

        try {
            String nSeq = CmmUtil.nvl(request.getParameter("nSeq"));

            log.info("nSeq : " + nSeq);

            NoticeDTO pDTO = new NoticeDTO();

            pDTO.setNoticeSeq(Long.parseLong(nSeq));

            noticeService.deleteNoticeInfo(pDTO);

            msg="삭제되었습니다.";

        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            log.info(this.getClass().getName() + ".noticeDelete End!");

            model.addAttribute("msg", msg);
        }

        return "/notice/MsgToList";
    }

    @GetMapping(value = "NoticeReg")
    public String noticeReg() {

        log.info(this.getClass().getName() + ".noticeReg Start!");

        log.info(this.getClass().getName() + ".noticeReg End!");

        return "/notice/NoticeReg";
    }


    @PostMapping(value = "NoticeInsert")
    public String noticeInsert(HttpSession session, HttpServletRequest request, ModelMap model) {

        log.info(this.getClass().getName() + ".noticeInsert Start!");

        String msg = "";

        try {
            String user_id = CmmUtil.nvl((String) session.getAttribute("SESSION_USER_ID"));
            String title = CmmUtil.nvl(request.getParameter("title"));
            String noticeYn = CmmUtil.nvl(request.getParameter("noticeYn"));
            String contents = CmmUtil.nvl(request.getParameter("contents"));


            log.info("user_id :"+user_id);
            log.info("title :"+title);
            log.info("noticeYn :"+noticeYn);
            log.info("contents :"+contents);

            NoticeDTO pDTO = new NoticeDTO();
            pDTO.setUserId(user_id);
            pDTO.setTitle(title);
            pDTO.setNoticeYn(noticeYn);
            pDTO.setContents(contents);

            noticeService.InsertNoticeInfo(pDTO);

            msg = "등록되었습니다.";


        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            log.info(this.getClass().getName() + ".noticeInsert End!");

            model.addAttribute("msg",msg);
        }

        return "/notice/MsgToList";

    }

}
