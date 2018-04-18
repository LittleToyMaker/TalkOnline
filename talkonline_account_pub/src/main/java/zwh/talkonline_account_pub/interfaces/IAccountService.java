package zwh.talkonline_account_pub.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("talkonlineAccount")
public interface IAccountService {

    @RequestMapping("signIn")
    String signIn(@RequestParam("params") String params);

    @RequestMapping("edit")
    String edit(@RequestParam("params") String params);

    @RequestMapping("get")
    String get(@RequestParam("params") String params);
}
