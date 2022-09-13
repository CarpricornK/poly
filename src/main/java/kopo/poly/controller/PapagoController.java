package kopo.poly.controller;

import kopo.poly.dto.PapagoDTO;
import kopo.poly.service.IPapagoService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping(value="papago")
@RestController
public class PapagoController {

    @Resource(name="PapagoService")
    private IPapagoService papagoService;

    @GetMapping(value = "detectLangs")
    public PapagoDTO detectLangs(HttpServletRequest request)
        throws Exception {
        log.info(this.getClass().getName() + ".detectLangs Start!");

        String text = CmmUtil.nvl(request.getParameter("text"));

        log.info("text : " + text);

        PapagoDTO pDTO = new PapagoDTO();
        pDTO.setText(text);

        PapagoDTO rDTO = papagoService.detectLangs(pDTO);

        if(rDTO == null) {
            rDTO = new PapagoDTO();
        }

        log.info(this.getClass().getName() + ".detectLangs End!");

        return rDTO;
    }

    @GetMapping(value = "translate")
    public PapagoDTO translate(HttpServletRequest request)
            throws Exception {
        log.info(this.getClass().getName() + ".translate Start!");

        System.out.println("translate 시작");
        String text = CmmUtil.nvl(request.getParameter("text"));

        log.info("text : " + text);

        PapagoDTO pDTO = new PapagoDTO();
        pDTO.setText(text);

        PapagoDTO rDTO = papagoService.translate(pDTO);

        if(rDTO == null) {
            rDTO = new PapagoDTO();
        }

        log.info(this.getClass().getName() + ".translate End!");

        return rDTO;
    }

}
