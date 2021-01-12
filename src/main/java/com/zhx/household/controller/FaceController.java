package com.zhx.household.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhx.household.service.FaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FaceController {
    @Autowired
    private FaceService faceService;

    @PostMapping("IdentifyImageFaceInfo")
    public String IdentifyImageFaceInfo(@RequestBody Map map) {
        JSONObject result = new JSONObject();
        if (map.get("imagedata") == null) {
            result.put("code", 1002);
            result.put("msg", "缺少imagedata参数");
        } else {
            result.put("code", 200);
            result.put("msg", "成功");
            result.put("data", faceService.IdentityFaceInfo(map.get("imagedata").toString()));
        }
        return result.toJSONString();
    }

    @PostMapping("TrafflcIdentifyImageFaceInfo")
    public String TrafflcIdentifyImageFaceInfo(@RequestBody Map map) {
        JSONObject result = new JSONObject();
        if (map.get("imagedata") == null) {
            result.put("code", 1002);
            result.put("msg", "缺少imagedata参数");
        } else if (map.get("id") == null) {
            result.put("code", 1002);
            result.put("msg", "缺少id参数");
        } else if (map.get("area") == null) {
            result.put("code", 1002);
            result.put("msg", "缺少area参数");
        } else {
            result.put("code", 200);
            result.put("msg", "成功");
            result.put("data", faceService.TrafflcIdentityFaceInfo(
                    map.get("imagedata").toString(),map.get("id").toString(),map.get("area").toString()
            ));
        }
        return result.toJSONString();
    }

    @GetMapping("test")
    public String test() {
        return "test";
    }
}
