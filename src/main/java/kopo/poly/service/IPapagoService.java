package kopo.poly.service;

import kopo.poly.dto.PapagoDTO;

public interface IPapagoService {

    String detectLangsApiURL = "https://openapi.naver.com/v1/papago/detectLangs";

    String translateApiURL = "https://openapi.naver.com/v1/papago/n2mt";

    PapagoDTO detectLangs(PapagoDTO pDTO) throws Exception;

    PapagoDTO translate(PapagoDTO pDTO) throws Exception;
}
