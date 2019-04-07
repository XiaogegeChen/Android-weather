# Android-weather
使用Retrofit2+Rxjava2+MVP实现第一行代码的cool weather<br>效果和书中实现的相同<br>
![0](https://github.com/XiaogegeChen/Android-weather/blob/master/readme_image/%E5%BC%95%E5%AF%BC.gif)
![1](https://github.com/XiaogegeChen/Android-weather/blob/master/readme_image/%E4%B8%BB%E9%A1%B5.gif)

## 工具封装
### 1、请求数据工具类
使用rxJava的firstElement操作符和concat操作符封装数据请求的过程，分为从本地获取和从网络获取，默认优先拿本地数据，可指定请求类型更改顺序，具体实现为util包下的[QueryDataHelper.java](https://github.com/XiaogegeChen/Android-weather/blob/master/app/src/main/java/com/xiaogege/jerry/util/helper/QueryDataHelper.java)类。
### 2、```Retrofit```单例封装以及通过添加```okhttp```拦截器动态更改```baseUrl```
由于```Retrofit```开销较大，封装成单例形式。由于数据从不同服务器调取，```baseUrl```不同，这里采用给请求添加自定义请求头的方式区别不同baseurl的请求，并在```Retrofit```单例中添加**okhttp拦截器**，通过对比头信息更改```baseurl```。具体实现为util包下的[RetrofitManager.java](https://github.com/XiaogegeChen/Android-weather/blob/master/app/src/main/java/com/xiaogege/jerry/util/manager/RetrofitManager.java)类。
### 3、```GsonConverterFactory```的封装
**retrofit+rxJava**请求网络通过```addConverterFactory()```方法直接将返回的json数据封装成javabean返回，但是对于非json数据就会报错。通过重写```GsonConverterFactory```可以更改转化的方式，对于非json数据不进行JavaBean的转化，直接返回。直接复制```retrofit2.converter.gson```包下的3个方法，并在```GsonResponseBodyConverter```类中重写```convert```方法即可，具体实现为util包下的[convert](https://github.com/XiaogegeChen/Android-weather/tree/master/app/src/main/java/com/xiaogege/jerry/util/convert)包。
### 4、```SharedPreferences```的封装
对```SharedPreferences```进行简单封装，使用时更方便。具体实现为util包下的[XmlIOUtils.java](https://github.com/XiaogegeChen/Android-weather/blob/master/app/src/main/java/com/xiaogege/jerry/util/XmlIOUtils.java)类。
