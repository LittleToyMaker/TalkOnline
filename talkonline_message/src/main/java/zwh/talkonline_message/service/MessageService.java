package zwh.talkonline_message.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zwh.talkonline_common.tool.ResultJsonSetter;
import zwh.talkonline_data_pub.flag.Flag;
import zwh.talkonline_data_pub.interfaces.IMessageMapper;
import zwh.talkonline_data_pub.model.Message;
import zwh.talkonline_message_pub.interfaces.IMessageService;

import java.util.Date;
import java.util.List;

@RestController
public class MessageService implements IMessageService {

    private static Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private IMessageMapper messageMapper;

    @RequestMapping("edit")
    public String edit(String params){
        logger.info("edit {}", new Object[]{params});
        JSONObject resJson = new JSONObject();
        Integer code = 0;
        String msg = null;
        JSONObject dataJson = new JSONObject();
        try{
            JSONObject paramJson = JSON.parseObject(params);
            Integer messageId = paramJson.getInteger("messageId");
            String content = paramJson.getString("content");
            Integer fromAccountId = paramJson.getInteger("fromAccountId");
            Integer toAccountId = paramJson.getInteger("toAccountId");

            if(StringUtils.isEmpty(content) || fromAccountId == null || toAccountId == null){
                code = 1;
                msg = "信息不全";
            }else {
                Message message = new Message();
                message.setId(messageId);
                message.setContent(content);
                message.setFromAccountId(fromAccountId);
                message.setToAccountId(toAccountId);
                message.setReadStatus(Flag.MSG_UNREAD);

                if(messageId == null){
                    message.setId(messageMapper.inert(message));
                    if(message.getId() == null){
                        code = 2;
                        msg = "db message创建失败";
                    }
                }else {
                    if(messageMapper.update(message) < 1){
                        code = 2;
                        msg = "db message更新失败";
                    }
                }
                dataJson.put("message", message);
            }
        }catch (Exception e){
            logger.error("edit 出现异常 ", e);
            code = 1;
            msg = "出现异常";
        }finally {
            ResultJsonSetter.setJson(resJson, code, msg, dataJson);
            return resJson.toJSONString();
        }
    }

    @RequestMapping("get")
    public String get(String params){
        logger.info("getMessage {}", new Object[]{params});
        JSONObject resJson = new JSONObject();

        Integer code = 0;
        String msg = null;
        JSONObject dataJson = new JSONObject();
        try{
            JSONObject paramJson = JSON.parseObject(params);
            Integer messageId = paramJson.getInteger("messageId");
            Integer fromAccountId = paramJson.getInteger("fromAccountId");
            Integer toAccountId = paramJson.getInteger("toAccountId");
            Date minCreateTime = paramJson.getDate("minCreateTime");
            Date maxCreateTime = paramJson.getDate("maxCreateTime");
            Integer status = paramJson.getInteger("status");
            Integer readStatus = paramJson.getInteger("readStatus");
            String content = paramJson.getString("content");
            Integer pageNo = paramJson.getInteger("pageNo");
            Integer pageSize = paramJson.getInteger("pageSize");

            Integer beginIndex = null;
            if(pageNo != null && beginIndex != null){
                beginIndex = (pageNo - 1) * pageSize;
            }

            if(messageId != null){
                List<Message> messages = messageMapper.selectByPage(messageId, null, null, null, null, null, null, null, null, null);
                if(messages != null && ! messages.isEmpty()){
                    dataJson.put("message", messages.get(0));
                }
            }else {
                List<Message> messages = messageMapper.selectByPage(messageId, status, readStatus, content, fromAccountId, toAccountId, minCreateTime, maxCreateTime, beginIndex, pageSize);
                long totalCount = messageMapper.count(messageId, status, readStatus, content, fromAccountId, toAccountId, minCreateTime, maxCreateTime);
                dataJson.put("messages", messages);
                dataJson.put("totalCount", totalCount);
            }
        }catch (Exception e){
            logger.error("getMessage 出现异常 ", e);
        }finally {
            ResultJsonSetter.setJson(resJson, code, msg, dataJson);
            return resJson.toJSONString();
        }
    }
}
