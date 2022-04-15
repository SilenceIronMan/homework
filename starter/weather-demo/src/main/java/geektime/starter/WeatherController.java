package geektime.starter;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class WeatherController {

	@Resource
	private WeatherService weatherService;

	@RequestMapping(value = "/currentcity", produces = { "text/plain;charset=UTF-8" })
	public Mono<String> printCity() {
		return Mono.just(weatherService.printCity());
	}

	@RequestMapping(value = "/weather", produces = { "text/plain;charset=UTF-8" })
	public Mono<String> weatherOfCity() throws Exception {
		return Mono.just(weatherService.getCityWeather());
	}

	@RequestMapping(value = "/weather_cache/{city}/{count}", produces = { "text/plain;charset=UTF-8" })
	public Mono<String> weatherOfCityCache(@PathVariable("city") String city,
										   @PathVariable("count") Integer count) throws Exception {
		DateTime start = DateUtil.date();
		for (int i = 0; i < count; i++) {
			weatherService.getCityWeatherCache(city);
		}
		DateTime end = DateUtil.date();
		String timeLog = "接口请求:" + DateUtil.between(start, end, DateUnit.MS) + "毫秒";
		log.info(timeLog);
		return Mono.just(timeLog);
	}

	@RequestMapping(value = "/weather/{city}/{count}", produces = { "text/plain;charset=UTF-8" })
	public Mono<String> weatherOfCity(@PathVariable("city") String city,
									  @PathVariable("count") Integer count) throws Exception {

		DateTime start = DateUtil.date();
		for (int i = 0; i < count; i++) {
			weatherService.getCityWeather(city);
		}
		DateTime end = DateUtil.date();
		String timeLog = "接口请求:" + DateUtil.between(start, end, DateUnit.MS) + "毫秒";
		log.info(timeLog);
		return Mono.just(timeLog);
	}
}
