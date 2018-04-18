package zwh.talkonline_web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionChecker implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SessionChecker.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        if(request.getSession().getAttribute("account") == null){
            response.setCharacterEncoding("UTF-8");
            ServletOutputStream outPut = response.getOutputStream();
            outPut.write("{\"code\":1,\"msg\":\"session失效\"}".getBytes());
            outPut.flush();
            return false;
        }
        logger.info("{}: params={}", new Object[]{request.getRequestURI(), request.getParameter("params")});
        return true;
    }
}
