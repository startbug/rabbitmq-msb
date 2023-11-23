package com.ggs.rabbitmq.demo.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggs.rabbitmq.demo.entity.Device;
import com.ggs.rabbitmq.demo.service.IDeviceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lhh
 * @since 2023-11-23
 */
@RestController
@Tag(name = "设备表", description = "设备表接口")
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private IDeviceService deviceService;

    @Operation(summary = "保存设备表", description = "保存设备表description")
    @PostMapping("/emqx/valid")
    public Map<String, String> valid() throws IOException {
        System.out.println("-----------" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Map<String, String> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> requestDataMap = objectMapper.readValue(request.getInputStream(), Map.class);
        String username = requestDataMap.get("username");
        String password = requestDataMap.get("password");
        Device device = deviceService.getOne(Wrappers.lambdaQuery(Device.class).eq(Device::getDeviceNo, username));
        if (device != null && password != null && password.equals(device.getDeviceSecret())) {
            map.put("result", "allow");
        } else {
            map.put("result", "deny");
        }
        return map;
    }

}
