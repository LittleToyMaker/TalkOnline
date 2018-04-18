package zwh.talkonline_message_pub.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("talkonlineMessage")
public interface IMessageService {

    @RequestMapping("edit")
    String edit(@RequestParam("params") String params);

    @RequestMapping("get")
    String get(@RequestParam("params") String params);
}
