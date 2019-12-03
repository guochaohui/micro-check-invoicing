package com.xs.micro.check.invoicing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StopWatch;


/**
 * 启动主入口
 *
 * @author guochaohui
 */
@SpringBootApplication
public class MicroCheckInvoicingApplication {

    private static final Logger LOG = LoggerFactory.getLogger(MicroCheckInvoicingApplication.class);

    /**
     * 用户服务执行入口<br>
     * 运行方式有三种：<br>
     * 1、直接在IDE中Run as 或 Debug as。（调试过程） <br>
     * 2、引入SpringBoot插件后通过 mvn spring-boot:run 运行（开发过程）
     * 3、普通jar包方式运行，需要先执行Maven打包，再执行 java -jar xxx.jar(生产环境)
     *
     * @param args
     */
    public static void main(String[] args) {
        StopWatch sw = new StopWatch();
        sw.start();
        SpringApplication.run(MicroCheckInvoicingApplication.class, args);
        sw.stop();

        LOG.info("\r\n" +
                "\r\n #########################################################################" +
                "\r\n #  程序启动完毕，总计耗时：{}", sw.getTotalTimeSeconds() +
                "\r\n #  请打开浏览器，输入后面的网址就可以使用了。http://localhost:11986/ " +
                "\r\n #########################################################################" +
                "\r\n");
    }

}
