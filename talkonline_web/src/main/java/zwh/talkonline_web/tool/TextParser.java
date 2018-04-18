package zwh.talkonline_web.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class TextParser {

    public static Map<String, String> parse(String lines){
        JSONArray linesArr = JSON.parseArray(lines);
        Map<String, String> res = new HashMap<>();
        if(lines != null && ! lines.isEmpty()){
            for(int i = 0; i < linesArr.size(); i ++){
                String line = linesArr.getString(i);
                int index = line.indexOf(":");
                res.put(line.substring(0, index), line.substring(index + 1, line.length()));
            }
        }
        return res;
    }
}
