package zwh.talkonline_data.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zwh.talkonline_data_pub.interfaces.IMessageMapper;
import zwh.talkonline_data_pub.model.Message;

import java.util.Date;
import java.util.List;

@Mapper
@Controller
@RequestMapping("message")
public interface MessageMapper extends IMessageMapper{

}
