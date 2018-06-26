package com.kute.controller.v1;

import com.kute.service.IConsumerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * created by kute on 2018/05/14 23:13
 */
@Controller
@RequestMapping("/api/v1/city")
public class CityController {

    @Resource
    private IConsumerService consumerService;

    /**
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/findcity/{code}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getUser(
            @PathVariable("code") String code) {
        String city = consumerService.findCity(code);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

}
