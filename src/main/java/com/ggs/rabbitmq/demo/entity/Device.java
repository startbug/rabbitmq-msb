package com.ggs.rabbitmq.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author lhh
 * @since 2023-11-23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("a_device")
@Schema(name = "设备表实体", description = "设备表实体description")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;
    @Schema(description = "主键", example = "0")
    private Long id;

    @Schema(description = "设备名称", example = "Redmi K60 Pro")
    private String name;

    @Schema(description = "设备编号", example = "AAAA")
    private String deviceNo;

    @Schema(description = "设备密钥", example = "WWWWW")
    private String deviceSecret;

    @Schema(description = "创建时间", example = "2023-11-23 11:11:11")
    private LocalDateTime createTime;

    @Schema(description = "最后更新时间", example = "2023-11-23 11:11:11")
    private LocalDateTime lastUpdateTime;

}
