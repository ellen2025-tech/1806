package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RunAppConsumerHystrix {

	public static void main(String[] args) {
		
		SpringApplication.run(RunAppConsumerHystrix.class, args);

	}

}
