package zwh.talkonline_data_pub.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zwh.talkonline_data_pub.model.Message;

import java.util.Date;
import java.util.List;

@FeignClient("talkonlineData")
public interface IMessageMapper {
    @RequestMapping("count")
    long count(@RequestParam("id") Integer id, @RequestParam("status") Integer status, @RequestParam("readStatus") Integer readStatus, @RequestParam("content") String content, @RequestParam("fromAccountId") Integer fromAccountId, @RequestParam("toAccountId") Integer toAccountId, @RequestParam("minCreateTime") Date minCreateTime, @RequestParam("maxCreateTime") Date maxCreateTime);
    @RequestMapping("selectByPage")
    List<Message> selectByPage(@RequestParam("id") Integer id, @RequestParam("status") Integer status, @RequestParam("readStatus") Integer readStatus, @RequestParam("content") String content, @RequestParam("fromAccountId") Integer fromAccountId, @RequestParam("toAccountId") Integer toAccountId, @RequestParam("minCreateTime") Date minCreateTime, @RequestParam("maxCreateTime") Date maxCreateTime, @RequestParam("beginIndex") Integer beginIndex, @RequestParam("limit") Integer limit);
    @RequestMapping("inert")
    int inert(@RequestBody Message message);
    @RequestMapping("update")
    int update(@RequestBody Message message);
}
