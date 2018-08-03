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
    @Reference(interfaceClass = ICityService.class)
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
    private ICityService cityService;

    @Override
    public User getUser(Integer userId) {

        RpcContext.getContext().setAttachment("akey", "avalue");

        logger.info("get user by rpc call:{}", userId);
        return userService.getUser(userId);
    }

    @Override
    public String findCity(String code, long timeOutMillis) {
        String result = "city-null";
        try{
            result = cityService.findCity(code, timeOutMillis);
        } catch(Exception e){
            logger.error("findCity exception", e);
        }
        return result;
    }

    @Override
    public String buildCity(String code) {
        String result = "null-value";
        try{
            cityService.buildCity(code);
        } catch (BuildCityException be) {
            logger.error("buildCity BuildCityException:{}", be.getMessage());
        } catch (BussinessException se) {
            logger.error("buildCity BussinessException:{}", se.getMessage());
        } catch(Exception e){
            logger.error("buildCity Exception:{}", e.getMessage());
        } catch(Throwable t){
            logger.error("buildCity Throwable:{}", t.getMessage());
        }
        return result;
    }
}
