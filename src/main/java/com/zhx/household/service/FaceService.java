package com.zhx.household.service;

import com.alibaba.fastjson.JSONObject;
import com.zhx.household.util.BodyAttr;
import org.springframework.stereotype.Service;

@Service
public class FaceService {

    public JSONObject IdentityFaceInfo(String imagedata){
//        String result = BodyAttr.body_attr(imagedata);
        String result = BodyAttr.body_byte(imagedata);
        return JSONObject.parseObject(result);
    }

    public JSONObject TrafflcIdentityFaceInfo(String imagedata,String id,String area){
        String result = BodyAttr.body_trafflc(imagedata,id,area);
        return JSONObject.parseObject(result);
    }

}
