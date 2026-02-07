package cn.tedu.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "provider-user") // 显式使用name属性
public interface HelloFeign {
	// 对提供者调用
	@RequestMapping("/hello/{name}")
	public String hello(@PathVariable("name") String name);

}
