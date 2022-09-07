package kopo.poly.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PapagoDTO {

    private String langCode;
    private String text;

    private String translatedText;
    private String scrLangType;
    private String tarLangType;

}
