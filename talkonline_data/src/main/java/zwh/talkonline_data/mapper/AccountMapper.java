package zwh.talkonline_data.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zwh.talkonline_data_pub.interfaces.IAccountMapper;
import zwh.talkonline_data_pub.model.Account;

import java.util.Date;
import java.util.List;

@Mapper
@RestController
@RequestMapping("account")
public interface AccountMapper extends IAccountMapper{

}
