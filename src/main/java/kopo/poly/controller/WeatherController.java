package kopo.poly.controller;

import kopo.poly.dto.WeatherDTO;
import kopo.poly.service.IWeatherService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping(value = "weather")
@RestController
public class WeatherController {

    @Resource(name = "WeatherService")
    private IWeatherService weatherService;


    @GetMapping(value="getWeather")
    public WeatherDTO getWeather(HttpServletRequest request)
        throws Exception {

        log.info(this.getClass().getName() + ".getWeather start!");

        String lat = CmmUtil.nvl(request.getParameter("lat"));
        String lon = CmmUtil.nvl(request.getParameter("lon"));

        WeatherDTO pDTO = new WeatherDTO();

        pDTO.setLat(lat);
        pDTO.setLon(lon);

        WeatherDTO rDTO = weatherService.getWeather(pDTO);

        if (rDTO == null) {
            rDTO = new WeatherDTO();
        }

        log.info(this.getClass().getName() + ".getWeather End!");

        return rDTO;
    }


}
