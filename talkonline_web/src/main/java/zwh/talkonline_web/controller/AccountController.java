package zwh.talkonline_web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zwh.talkonline_account_pub.interfaces.IAccountService;
import zwh.talkonline_common.tool.ResultJsonSetter;
import zwh.talkonline_data_pub.model.Account;
import zwh.talkonline_web.tool.TextParser;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private IAccountService accountService;

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public String getAccountInfo(HttpServletRequest request, @RequestParam("id") Integer id, @RequestParam("name") String name){
        JSONObject resJson = new JSONObject();
        try{
            if(id == null && name == null){
                Account account = (Account) request.getSession().getAttribute("account");
                resJson = JSON.parseObject(JSON.toJSONString(account));
                resJson.put("password", null);
            }
        }catch (Exception e){
            logger.error("getAccount 出现异常", e);
            ResultJsonSetter.setJson(resJson, 1, "web出现异常", null);
        }finally {
            return resJson.toJSONString();
        }
    }

    @RequestMapping(value = "signIn", method = RequestMethod.POST)
    public String signIn(HttpServletRequest request, @RequestBody String lines){
        JSONObject resJson = new JSONObject();
        try{
            resJson = JSON.parseObject(accountService.signIn(JSON.toJSONString(TextParser.parse(lines.replace("lines=", "")))));
            if(resJson.getInteger("code") == 0){
                request.getSession().setAttribute("account", JSON.parseObject(resJson.getJSONObject("data").getString("account"), Account.class));
            }
        }catch (Exception e){
            logger.error("signIn 出现异常", e);
            ResultJsonSetter.setJson(resJson, 1, "web出现异常", null);
        }finally {
            return resJson.toJSONString();
        }
    }

    @RequestMapping(value = "signUp")
    public String signUp(HttpServletRequest request, @RequestBody String lines){
        JSONObject resJson = new JSONObject();
        try{
            resJson = JSON.parseObject(accountService.edit(JSON.toJSONString(TextParser.parse(lines.replace("lines=", "")))));
        }catch (Exception e){
            logger.error("signUp 出现异常 ", e);
            ResultJsonSetter.setJson(resJson, 1, "web出现异常", null);
        }finally {
            return resJson.toJSONString();
        }
    }

    @RequestMapping(value = "edit")
    public String edit(HttpServletRequest request, @RequestBody String lines){
        JSONObject resJson = new JSONObject();
        try{
            Account account = (Account) request.getSession().getAttribute("account");
            System.out.println(JSON.toJSONString(account));
            JSONObject paramJson = JSON.parseObject(JSON.toJSONString(TextParser.parse(lines.replace("lines=", ""))));
            paramJson.put("accountId", account.getId());
            resJson = JSON.parseObject(accountService.edit(paramJson.toJSONString()));
        }catch (Exception e){
            logger.error("edit 出现异常 ", e);
            ResultJsonSetter.setJson(resJson, 1, "web出现异常", null);
        }finally {
            return resJson.toJSONString();
        }
    }
}
