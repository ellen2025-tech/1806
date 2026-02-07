package cn.tedu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.feign.HelloFeign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
public class HelloController {
	// 注入Feign接口
	@Autowired
	private HelloFeign helloFeign;

	@RequestMapping("/hello/{name}")
	@CircuitBreaker(name = "helloService", fallbackMethod = "HelloFallback")
	public String hello(@PathVariable String name) {
		return helloFeign.hello(name);
	}

	public String HelloFallback(String name, Throwable throwable) {
		return "Resilience4j-default-tony，原因：" + throwable.getMessage();
	}

}
