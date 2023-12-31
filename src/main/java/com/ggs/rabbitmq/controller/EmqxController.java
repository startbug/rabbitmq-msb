package com.ggs.rabbitmq.controller;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggs.rabbitmq.demo.entity.Device;
import com.ggs.rabbitmq.demo.service.IDeviceService;

/**
 * @Author starbug
 * @Description
 * @Datetime 2023/11/22 22:35
 */
@RestController
@RequestMapping("/emqx")
public class EmqxController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private IDeviceService deviceService;

    @PostMapping("/valid")
    public Map<String, String> valid() throws IOException {
        System.out.println("-----------" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Map<String, String> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> requestDataMap = objectMapper.readValue(request.getInputStream(), Map.class);
        deviceService.getOne(Wrappers.lambdaQuery(Device.class).eq(Device::getDeviceNo, requestDataMap.get("username")));
        map.put("result", "deny");
        return map;
    }

}
