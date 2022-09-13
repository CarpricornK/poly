package kopo.poly.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.PapagoDTO;
import kopo.poly.service.IPapagoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.NetworkUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service("PapagoService")
public class PapagoService implements IPapagoService {

    @Value("${naver.papago.clientId}")
    private String clientId;

    @Value("${naver.papago.clientSecret}")
    private String clientSecret;

    private Map<String, String> setNaverInfo() {
        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("X-Naver-Client-Id", clientId);
        requestHeader.put("X-Naver-Client-Secret", clientSecret);

        log.info("Client-Id :" + clientId);
        log.info("Client-Secret :" + clientSecret);


        return requestHeader;
    }

    @Override
    public PapagoDTO detectLangs(PapagoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".detectLangs Start");

        String text = CmmUtil.nvl(pDTO.getText());

        String param = "query=" + URLEncoder.encode(text,"UTF-8");

        String json = NetworkUtil.post(IPapagoService.detectLangsApiURL, this.setNaverInfo(), param);

        log.info("json :" + json);

        PapagoDTO rDTO = new ObjectMapper().readValue(json, PapagoDTO.class);

        rDTO.setText(text);

        log.info(this.getClass().getName() + ".detectLangs End");

        return rDTO;
    }


    @Override
    public PapagoDTO translate(PapagoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".translate Start");

        PapagoDTO rDTO = this.detectLangs(pDTO);

        String langCode = CmmUtil.nvl(rDTO.getLangCode());

        rDTO = null;

        String source = "";
        String target = "";

        if(langCode.equals("ko")) {
            source = "ko";
            target = "en";

        } else if (langCode.equals("en")) {
            source = "en";
            target = "ko";

        } else {
            new Exception("한국어와 영어만 번역합니다");
        }

        String text = CmmUtil.nvl(pDTO.getText());

        String postParams = "source=" + source + "&target=" + target + "&text=" + URLEncoder.encode(text, "UTF-8");

        log.info("postParams : " + postParams);

        String json = NetworkUtil.post(IPapagoService.translateApiURL, this.setNaverInfo(), postParams);

        log.info("json : " + json);

        Map<String, Object> rMap = new ObjectMapper().readValue(json, LinkedHashMap.class);

        Map<String, Object> messageMap = (Map<String, Object>) rMap.get("message");

        Map<String, String> resultMap = (Map<String, String>) messageMap.get("result");

        log.info("resultMap : " + resultMap);

        String srcLangType = CmmUtil.nvl(resultMap.get("srcLangType"));
        String tarLangType = CmmUtil.nvl(resultMap.get("tarLangType"));
        String translatedText = CmmUtil.nvl(resultMap.get("translatedText"));

        System.out.println("srcLangType : " + srcLangType);
        System.out.println("tarLangType : " + tarLangType);
        System.out.println("translatedText : " + translatedText);

        log.info("srcLangType : " + srcLangType);
        log.info("tarLangType : " + tarLangType);
        log.info("translatedText : " + translatedText);

        rDTO = new PapagoDTO();
        rDTO.setText(text);
        rDTO.setTranslatedText(translatedText);
        rDTO.setScrLangType(srcLangType);
        rDTO.setTarLangType(tarLangType);

        resultMap = null;
        messageMap = null;
        rMap = null;

        log.info(this.getClass().getName() + ".translate End");

        return rDTO;
    }

}
