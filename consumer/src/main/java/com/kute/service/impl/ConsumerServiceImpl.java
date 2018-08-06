package com.kute.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.cluster.LoadBalance;
import com.alibaba.dubbo.rpc.cluster.loadbalance.ConsistentHashLoadBalance;
import com.kute.domain.User;
import com.kute.exception.BuildCityException;
import com.kute.exception.BussinessException;
import com.kute.service.ICityService;
import com.kute.service.IConsumerService;
import com.kute.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * created by kute on 2018/05/14 01:36
 */
@Service("consumerService")
public class ConsumerServiceImpl implements IConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);

    @Resource
    private IUserService userService;

    /**
     * 通过注解 引入 服务
     */
//    @Reference(interfaceClass = ICityService.class)
    /**
     * 设置方法级别的重试次数
     */
//    @Reference(interfaceClass = ICityService.class, retries =5, timeout = 5000, parameters = {"findCity.retries", "2"})
    /**
     * 设置该接口容错模式为 快速失败，只调用一次，主要针对 非幂等接口
     */
//    @Reference(interfaceClass = ICityService.class, cluster = "failfast")

    /**
     * 设置 ICityService 接口 容错模式为 failover，默认重试次数为5，而单独设置 buildCity方法（非幂等）的 容错模式为快速失败
     */
//    @Reference(interfaceClass = ICityService.class, cluster = "failover", retries = 5, parameters = {"buildCity.cluster", "failfast"})

    /**
     * 设置服务降级:
     * @see com.alibaba.dubbo.rpc.cluster.support.wrapper.MockClusterInvoker
     * mock属性值：
     *    false: 不降级
     *    true: 服务调用失败后，调用mock服务接口进行降级
     *    default: 服务调用失败后，调用mock服务接口进行降级
     *    forece: 强制 调用mock服务接口进行降级，无论 接口调用是否成功
     * mock服务接口类定义规则：接口+Mock，如 ICityServiceMock，注意 此类的package路径要和接口一致，如果不一致则需要直接在mock参数里指明 此类
     */
//    @Reference(interfaceClass = ICityService.class, retries = 5, check = false, mock = "com.kute.service.mock.ICityServiceMock")
    /**
     *  对于 想单独为 某个方法设置 降级mock，可以在 parameters 中设置
     *  如下 设置了 findCity 重试次数（不重试，针对 非幂等接口 就这么设置），然后如果失败了就 调用降级mock
     */
    @Reference(interfaceClass = ICityService.class, retries = 5, parameters = {"findCity.mock", "com.kute.service.mock.method.ICityServiceFindCityMock", "findCity.retries", "0"})
    private ICityService cityService;

    @Override
    public User getUser(Integer userId) {

        logger.info("get user by rpc call:{}", userId);
        return userService.getUser(userId);
    }

    @Override
    public String findCity(String code, long timeOutMillis) {
        String result = "city-null";
        try {
            result = cityService.findCity(code, timeOutMillis);
        } catch (Exception e) {
            logger.error("findCity exception", e);
        }
        return result;
    }

    @Override
    public String buildCity(String code) {
        String result = "null-value";
        try {
            cityService.buildCity(code);
        } catch (BuildCityException be) {
            logger.error("buildCity BuildCityException:{}", be.getMessage());
        } catch (BussinessException se) {
            logger.error("buildCity BussinessException:{}", se.getMessage());
        } catch (Exception e) {
            logger.error("buildCity Exception:{}", e.getMessage());
        } catch (Throwable t) {
            logger.error("buildCity Throwable:{}", t.getMessage());
        }
        return result;
    }

    @Override
    public String liveCity(String code, long timeOutMillis) {
        logger.info("consumer-livecity-begin:{}", code);

        // 传递 隐士参数
        RpcContext.getContext().setAttachment("livecity_start_key", "0");

        String result = cityService.liveCity(code, timeOutMillis);
        logger.info("consumer-livecity-over:{}", result);
        return result;
    }
}
