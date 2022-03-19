package geektime.starter;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
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

	@RequestMapping(value = "/weather/{city}", produces = { "text/plain;charset=UTF-8" })
	public Mono<String> weatherOfCity(@PathVariable("city") String city) throws Exception {

		return Mono.just(weatherService.getCityWeather(city));
	}
}
