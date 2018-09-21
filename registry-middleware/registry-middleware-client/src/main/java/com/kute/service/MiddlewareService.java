package com.kute.service;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.kute.api.IDispatchService;
import com.kute.exception.MiddlewareException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * created by kute on 2018/09/21 10:57
 */
@Service("middlewareService")
public class MiddlewareService implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(MiddlewareService.class);

    @Resource(name = "dubboApplicationConfig")
    private ApplicationConfig application;
    @Resource(name = "dubboRegistryConfig")
    private RegistryConfig registry;

    /**
     *
     * @param module
     *        客户端模块名称
     * @param referenceParamMap
     *        附加参数
     * @param beanName
     *        目标接口类名
     * @param methodName
     *        目标接口方法名
     * @param methodParamMap
     *        方法参数
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public Object doDispatch(String module, Map<String, String> referenceParamMap, String beanName, String methodName, Map<String, Object> methodParamMap) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        IDispatchService dispatchService;
        try {
            dispatchService = getDubboService(module, IDispatchService.class, referenceParamMap);
        } catch (Exception e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("There is no available service[{}] to use", IDispatchService.class.getName(), e);
            }
            return new MiddlewareException("There is no available service to use.");
        }
        return dispatchService.dispatch(beanName, methodName, methodParamMap);
    }

    public Object doDispatch(String module, String beanName, String methodName, Map<String, Object> methodParamMap) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return doDispatch(module, null, beanName, methodName, methodParamMap);
    }

    public <T> T getDubboService(String module, Class<T> iClazz, Map<String, String> referenceParamMap) {
        ReferenceConfig<T> reference = new ReferenceConfig<T>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(iClazz);
        reference.setGroup(module);
        if (null != referenceParamMap && referenceParamMap.size() > 0) {
            reference.setParameters(referenceParamMap);
        }
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        return cache.get(reference);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
