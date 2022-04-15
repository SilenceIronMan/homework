### 如何执行程序

```
程序可通过 java -jar weather-demo-0.1.jar 执行
```



## 获取城市天气固定次数并返回耗时（无缓存)

http://localhost:8080/weather/{city}/{count}

{city} 可替换成需要的城市

{count} 可替换成执行次数



如 http://localhost:8080/weather/南京/20    表示查询南京天气20次 



## 获取城市天气固定次数并返回耗时 （缓存)

http://localhost:8080/weather_cache/{city}/{count}

{city} 可替换成需要的城市

{count} 可替换成执行次数



如 http://localhost:8080/weather_cache/南京/20    表示查询南京天气20次 


![缓存](https://user-images.githubusercontent.com/39050241/163540976-d118eb37-6838-4eae-b0dd-f196667ae2a6.png)


![非缓存](https://user-images.githubusercontent.com/39050241/163540993-d982c5f6-e510-47ad-92c6-3b9d9bf453c7.png)

