package zwh.talkonline_account.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zwh.talkonline_account_pub.interfaces.IAccountService;
import zwh.talkonline_common.tool.ResultJsonSetter;
import zwh.talkonline_data_pub.flag.Flag;
import zwh.talkonline_data_pub.interfaces.IAccountMapper;
import zwh.talkonline_data_pub.model.Account;

import java.util.List;

@RestController
public class AccountService implements IAccountService{

    private static Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private IAccountMapper accountMapper;

    @Override
    @RequestMapping("signIn")
    public String signIn(String params){
        logger.info("signIn {}", new Object[]{params});
        JSONObject resJson = new JSONObject();

        Integer code = 0;
        String msg = null;
        JSONObject dataJson = new JSONObject();
        try{
            JSONObject paramJson = JSON.parseObject(params);
            String accountName = paramJson.getString("accountName");
            String password = paramJson.getString("password");
            if(StringUtils.isNotEmpty(accountName) && StringUtils.isNotEmpty(password)){
                Account account = accountMapper.selectByName(accountName);

                if(account != null && account.getPassword().equals(password)){
                    dataJson.put("account", account);
                }else {
                    code = 1;
                    msg = "账号不存在或者密码错误";
                }
            }else {
                code = 1;
                msg = "登录信息不全";
            }
        }catch (Exception e){
            logger.error("signIn 出现异常 ", e);
            code = 2;
            msg = "出现异常";
        }finally {
            ResultJsonSetter.setJson(resJson, code, msg, dataJson);
            return resJson.toJSONString();
        }
    }

    @Override
    @RequestMapping("edit")
    public String edit(String params){
        logger.info("edit {}", new Object[]{params});
        JSONObject resJson = new JSONObject();

        Integer code = 0;
        String msg = null;
        JSONObject dataJson = new JSONObject();
        try{
            JSONObject paramJson = JSON.parseObject(params);
            Integer accountId = paramJson.getInteger("accountId");
            String accountName = paramJson.getString("accountName");
            String password = paramJson.getString("password");
            String headshot = paramJson.getString("headshot");

            if(StringUtils.isEmpty(accountName) || StringUtils.isEmpty(password)){
                code = 1;
                msg = "注册信息不全";
            }else {
                Account account = new Account();
                account.setId(accountId);
                account.setName(accountName);
                account.setPassword(password);
                account.setHeadshot(headshot);

                if(password.length() < 6){
                    code = 1;
                    msg = "密码长度不能少于6位";
                }else if(accountMapper.selectByName(accountName) != null && accountId == null){
                    code = 1;
                    msg = "用户名已存在";
                }else {
                    if(accountId == null){
                        account.setId(accountMapper.insert(account));
                        if(account.getId() == null){
                            code = 2;
                            msg = "db account 创建失败";
                        }
                    }else {
                        if(accountMapper.update(account) < 1){
                            code = 2;
                            msg = "db account 更新失败";
                        }
                    }
                    dataJson.put("account", account);
                }
            }
        }catch (Exception e){
            logger.error("signUp 出现异常 ", e);
            code = 3;
            msg = "出现异常";
        }finally {
            ResultJsonSetter.setJson(resJson, code, msg, dataJson);
            return resJson.toJSONString();
        }
    }

    @Override
    @RequestMapping("get")
    public String get(String params){
        logger.info("getAccounts {}", new Object[]{params});
        JSONObject resJson = new JSONObject();

        Integer code = 0;
        String msg = null;
        JSONObject dataJson = new JSONObject();
        try{
            JSONObject paramJson = JSON.parseObject(params);
            Integer pageNo = paramJson.getInteger("pageNo");
            Integer pageSize = paramJson.getInteger("pageSize");
            Integer accountId = paramJson.getInteger("accountId");

            Integer beginIndex = null;
            if(pageNo != null && beginIndex != null){
                beginIndex = (pageNo - 1) * pageSize;
            }

            if(accountId == null){
                List<Account> accounts = accountMapper.selectByPage(Flag.ACTIVE, null, null, null, null, beginIndex, pageSize);
                dataJson.put("accounts", accounts);
            }else {
                Account account = accountMapper.selectById(accountId);
                dataJson.put("account", account);
            }
        }catch (Exception e){
            logger.error("getAccounts 出现异常 ", e);
            code = 1;
            msg = "出现异常";
        }finally {
            ResultJsonSetter.setJson(resJson, code, msg, dataJson);
            return resJson.toJSONString();
        }
    }
}
