package com.xs.micro.check.invoicing.config.bean;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger 配置
 * @author guochaohui
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	/**
	 * 
	 * @Title: createRestApi
	 * @Description:
	 * @author LIJUN  
	 * @date 2018年8月14日  
	 * @return
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				// 本地、开发、测试环境扫描真正的Controller包
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("微服务-开票数据校验").description("开票数据校验").termsOfServiceUrl("")
				.contact(new Contact("guochaohui", "guochaohui", "343802696@qq.com")).version("1.0").build();
	}
}
