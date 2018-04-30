package zwh.talkonline_web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
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

    @RequestMapping(value = "get", method = RequestMethod.POST)
    public String getAccountInfo(HttpServletRequest request, @RequestBody String params){
        logger.info("get {}", new Object[]{params});
        JSONObject resJson = new JSONObject();
        JSONObject dataJson = new JSONObject();
        try{
            JSONObject paramJson = JSON.parseObject(params);
            Integer accountId = paramJson.getInteger("id");
            String name = paramJson.getString("name");
            if(accountId != null && accountId == -1){
                Account account = (Account) request.getSession().getAttribute("account");
                dataJson.put("account", JSON.parseObject(JSON.toJSONString(account)));
                dataJson.getJSONObject("account").put("password", null);

                resJson.put("code", 0);
                resJson.put("data", dataJson);
            }else if(StringUtils.isNotEmpty(name)){

            }else {
                resJson =  JSON.parseObject(accountService.get(params));
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
        logger.info("signIn {}", new Object[]{lines});
        JSONObject resJson = new JSONObject();
        try{
            resJson = JSON.parseObject(accountService.signIn(JSON.toJSONString(TextParser.parse(lines))));
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
        logger.info("signUp {}", new Object[]{lines});
        JSONObject resJson = new JSONObject();
        try{
            resJson = JSON.parseObject(accountService.edit(JSON.toJSONString(TextParser.parse(lines))));
        }catch (Exception e){
            logger.error("signUp 出现异常 ", e);
            ResultJsonSetter.setJson(resJson, 1, "web出现异常", null);
        }finally {
            return resJson.toJSONString();
        }
    }

    @RequestMapping(value = "edit")
    public String edit(HttpServletRequest request, @RequestBody String lines){
        logger.info("edit {}", new Object[]{lines});
        JSONObject resJson = new JSONObject();
        try{
            Account account = (Account) request.getSession().getAttribute("account");
            System.out.println(JSON.toJSONString(account));
            JSONObject paramJson = JSON.parseObject(JSON.toJSONString(TextParser.parse(lines)));
            paramJson.put("accountId", account.getId());
            resJson = JSON.parseObject(accountService.edit(paramJson.toJSONString()));
        }catch (Exception e){
            logger.error("edit 出现异常 ", e);
            ResultJsonSetter.setJson(resJson, 1, "web出现异常", null);
        }finally {
            return resJson.toJSONString();
        }
    }

    @RequestMapping(value = "/exit/{accountName}", method = RequestMethod.GET)
    public String isExit(HttpServletRequest request, @PathVariable String accountName){
        logger.info("isExit {}", new Object[]{accountName});
        JSONObject resJson = new JSONObject();
        try{
            JSONObject paramJson = new JSONObject();
            paramJson.put("accountName", accountName);
            resJson = JSON.parseObject(accountService.get(paramJson.toJSONString()));
            if(resJson.getInteger("code") == 0 && resJson.getJSONObject("data") != null){
                resJson.put("data", true);
            }else {
                resJson.put("data", false);
            }
        }catch (Exception e){
            logger.error("isExit 出现异常 ", e);
            ResultJsonSetter.setJson(resJson, 1, "web出现异常", false);
        }finally {
            return resJson.toJSONString();
        }
    }
}
