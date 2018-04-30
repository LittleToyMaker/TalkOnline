package zwh.talkonline_data_pub.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zwh.talkonline_data_pub.model.Account;

import java.util.Date;
import java.util.List;

@FeignClient("talkonlineData")
@RequestMapping("account")
public interface IAccountMapper {
    @RequestMapping("selectById")
    Account selectById(@RequestParam("id") Integer id);
    @RequestMapping("selectByName")
    Account selectByName(@RequestParam("name") String name);
    @RequestMapping("selectByPage")
    List<Account> selectByPage(
            @RequestParam(value = "status", required = false) Integer status
            , @RequestParam(value = "minCreateTime", required = false) Date minCreateTime
            , @RequestParam(value = "maxCreateTime", required = false) Date maxCreateTime
            , @RequestParam(value = "minUpdateTime", required = false) Date minUpdateTime
            , @RequestParam(value = "maxUpdateTime", required = false) Date maxUpdateTime
            , @RequestParam(value = "beginIndex", required = false) Integer beginIndex
            , @RequestParam(value = "limit", required = false) Integer limit);
    @RequestMapping("count")
    Integer count(@RequestParam(value = "status", required = false) Integer status
            , @RequestParam(value = "minCreateTime", required = false) Date minCreateTime
            , @RequestParam(value = "maxCreateTime", required = false) Date maxCreateTime
            , @RequestParam(value = "minUpdateTime", required = false) Date minUpdateTime
            , @RequestParam(value = "maxUpdateTime", required = false) Date maxUpdateTime);
    @RequestMapping("insert")
    Integer insert(@RequestBody Account account);
    @RequestMapping("update")
    Integer update(@RequestBody Account account);
}
