package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableDiscoveryClient // 启用服务发现客户端
public class RunAppZuul {

	public static void main(String[] args) {

		SpringApplication.run(RunAppZuul.class, args);

	}
}