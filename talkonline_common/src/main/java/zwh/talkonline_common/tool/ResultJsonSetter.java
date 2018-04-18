package zwh.talkonline_common.tool;

import com.alibaba.fastjson.JSONObject;

public class ResultJsonSetter {

    public static void setJson(JSONObject resJson, Integer code, String msg, Object data){
        if(resJson == null){
            resJson = new JSONObject();
        }
        resJson.put("code", code);
        resJson.put("msg", msg);
        resJson.put("data", data);
    }
}
