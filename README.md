# Android-weather
使用Retrofit2+Rxjava2+MVP实现第一行代码的cool weather<br>效果和书中实现的相同<br>
![0](https://github.com/XiaogegeChen/Android-weather/blob/master/readme_image/%E5%BC%95%E5%AF%BC.gif)
![1](https://github.com/XiaogegeChen/Android-weather/blob/master/readme_image/%E4%B8%BB%E9%A1%B5.gif)

## 工具封装
### 1、请求数据工具类
使用rxJava的firstElement操作符和concat操作符封装数据请求的过程，分为从本地获取和从网络获取，默认优先拿本地数据，可指定请求类型更改顺序，详情查看[QueryDataHelper.java](https://github.com/XiaogegeChen/Android-weather/blob/master/app/src/main/java/com/xiaogege/jerry/util/helper/QueryDataHelper.java)
