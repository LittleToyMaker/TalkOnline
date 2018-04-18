package zwh.talkonline_web.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;

@Aspect
@Component
public class ParamProcessAgency {

    private static final Logger logger = LoggerFactory.getLogger(ParamProcessAgency.class);

    @Pointcut("execution(* zwh.talkonline_web.controller.*.*(..))")
    public void controllerPointCut(){};

    @Around("controllerPointCut()")
    public Object process(ProceedingJoinPoint joinPoint){
        String res = "{\"msg\":\"DecoderAgency 出现异常\"}";
        Object[] args = joinPoint.getArgs();
        try{
            if(args != null){
                for(int i = 0; i < args.length; i ++){
                    if(args[i] instanceof String){
                        String argStr = (String) args[i];
                        args[i] = URLDecoder.decode(argStr, "UTF-8");
                    }
                }
            }
            res = (String) joinPoint.proceed(args);
        }catch (Exception e){
            logger.error("decode 出现异常 ", e);
        }finally {
            return res;
        }
    }
}
