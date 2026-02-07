package cn.tedu.fallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 替代 ZuulFallbackProvider 的Gateway Fallback控制器 对应 Resilience4j CircuitBreaker 的
 * fallbackUri 配置
 */
@RestController
@RequestMapping("/fallback")
public class GatewayFallbackController {

	/**
	 * 替代 ZuulFallbackProvider 的 fallbackResponse 方法 对应 provider-user 服务的降级处理
	 */
	@GetMapping("/user")
	public Mono<ResponseEntity<Map<String, Object>>> userServiceFallback() {
		return createFallbackResponse("provider-user服务暂时不可用，请稍后重试", "USER_SERVICE_UNAVAILABLE", 503);
	}

	/**
	 * 对应 consumer-hystrix 服务的降级处理
	 */
	@GetMapping("/hystrix")
	public Mono<ResponseEntity<Map<String, Object>>> hystrixServiceFallback() {
		return createFallbackResponse("consumer-hystrix服务暂时不可用，请稍后重试", "HYSTRIX_SERVICE_UNAVAILABLE", 503);
	}

	/**
	 * 全局默认降级处理 - 替代 ZuulFallbackProvider 的 getRoute() 返回 "*"
	 */
	@GetMapping("/default")
	public Mono<ResponseEntity<Map<String, Object>>> defaultFallback() {
		return createFallbackResponse("服务暂时不可用，请稍后重试", "DEFAULT_SERVICE_UNAVAILABLE", 503);
	}

	/**
	 * 创建统一的降级响应 替代 ZuulFallbackProvider 的 fallbackResponse() 方法
	 */
	private Mono<ResponseEntity<Map<String, Object>>> createFallbackResponse(String message, String errorCode,
			int statusCode) {

		Map<String, Object> result = new HashMap<>();
		result.put("success", false);
		result.put("code", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		result.put("data", null);
		result.put("timestamp", System.currentTimeMillis());
		result.put("fallback", true); // 标记为降级响应

		HttpStatus httpStatus = HttpStatus.valueOf(statusCode);

		return Mono.just(ResponseEntity.status(httpStatus).body(result));
	}
}