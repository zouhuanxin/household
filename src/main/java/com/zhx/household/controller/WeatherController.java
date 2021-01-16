package com.zhx.household.controller;

import com.alibaba.fastjson.JSON;
import com.zhx.household.service.WeatherService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    /**
     *  当日渣津天气查询
     *  http://forecast.weather.com.cn/town/weather1dn/101240212034.shtml#input
     */
    @GetMapping("getTodayWeather")
    public String getTodayWeather(){
        JSONObject rep = new JSONObject();
        try {
            rep.put("code",200);
            rep.put("data",weatherService.getTodayWeather());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rep.toString();
    }

}
