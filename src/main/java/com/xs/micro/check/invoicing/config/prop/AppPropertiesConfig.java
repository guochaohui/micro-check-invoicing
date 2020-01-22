package com.xs.micro.check.invoicing.config.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 应用配置类，应用级别的所有配置统一入口<br>
 * 不建议将配置进行分散管理，原则上一个项目中仅有一个@ConfigurationProperties注解文件。<br>
 * 并且建议应用级别配置均以app.conf开头，用以和框架配置做命名空间区分
 *
 * @author guochaohui
 */
@Component
@ConfigurationProperties(prefix = "app.conf")
public class AppPropertiesConfig {

}