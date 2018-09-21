package com.kute.controller.v1;

import com.kute.service.MiddlewareService;
import com.kute.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * created by kute on 2018/09/21 17:32
 */
@RestController
@RequestMapping("/api/v1/gateway")
public class ApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

    @Resource
    private MiddlewareService middlewareService;

    @RequestMapping(value = "/dubboservice/{module}/{beanName}/{methodName}", method = {RequestMethod.GET, RequestMethod.POST})
    public Object dubboService(
            @PathVariable("module") String module,
            @PathVariable("beanName") String beanName,
            @PathVariable("methodName") String methodName,
            @RequestParam(required = false) Map<String, Object> methodParamMap,
            @RequestParam(required = false) Map<String, String> referenceParamMap) {

        if(StringUtil.isAnyNullOrEmpty(module, beanName, methodName)) {
            throw new IllegalArgumentException("Param is illegal[null]");
        }

        try{
            return middlewareService.doDispatch(module, referenceParamMap, beanName, methodName, methodParamMap);
        } catch(Exception e){
            LOGGER.error("Gateway dubbo service error, module={}, beanName={}, methodName={}, methodParamMap={}, referenceParamMap={}, e={}", new Object[]{
                    module, beanName, methodName, methodParamMap.toString(), referenceParamMap.toString(), e
            });

        }
        return null;
    }

}
