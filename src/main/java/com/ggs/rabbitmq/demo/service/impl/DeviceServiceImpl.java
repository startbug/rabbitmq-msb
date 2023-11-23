package com.ggs.rabbitmq.demo.service.impl;

import com.ggs.rabbitmq.demo.entity.Device;
import com.ggs.rabbitmq.demo.mapper.DeviceMapper;
import com.ggs.rabbitmq.demo.service.IDeviceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lhh
 * @since 2023-11-23
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

}
