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
    List<Account> selectByPage(@RequestParam("status") Integer status, @RequestParam("minCreateTime") Date minCreateTime, @RequestParam("maxCreateTime") Date maxCreateTime, @RequestParam("minUpdateTime") Date minUpdateTime, @RequestParam("maxUpdateTime") Date maxUpdateTime, @RequestParam("beginIndex") Integer beginIndex, @RequestParam("limit") Integer limit);
    @RequestMapping("insert")
    int insert(@RequestBody Account account);
    @RequestMapping("update")
    int update(@RequestBody Account account);
}
