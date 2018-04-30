package zwh.talkonline_data.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zwh.talkonline_data_pub.interfaces.IAccountMapper;

@Mapper
@RestController
@RequestMapping("account")
public interface AccountMapper extends IAccountMapper{

}
