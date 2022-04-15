### 如何执行程序

```
程序可通过 java -jar weather-demo-0.1.jar 执行
```



## 获取城市天气固定次数并返回耗时（不带带缓存)

http://localhost:8080/weather/{city}/{count}

{city} 可替换成需要的城市

{count} 可替换成需要的城市



如 http://localhost:8080/weather/苏州/20    表示查询苏州天气20次 



## 获取城市天气固定次数并返回耗时 （带缓存)

http://localhost:8080/weather_cache/{city}/{count}

{city} 可替换成需要的城市

{count} 可替换成需要的城市



如 http://localhost:8080/weather_cache/苏州/20    表示查询苏州天气20次 





