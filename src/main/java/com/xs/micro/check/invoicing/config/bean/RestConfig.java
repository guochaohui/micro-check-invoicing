package com.xs.micro.check.invoicing.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
/**
 * 
* @Title: RestConfig.java  
* @Description:
* @author LIJUN  
* @date 2018年8月14日
 */
@Configuration
public class RestConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
