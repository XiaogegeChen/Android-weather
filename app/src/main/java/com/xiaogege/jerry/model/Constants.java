package com.xiaogege.jerry.model;

public class Constants {
    /**
     * intent 参数的key
     */
    public static final String INTENT_PARAM_NAME = "intent_param_name";

    public static final String IS_JUMP = "is_jump";
    public static final String IS_JUMP_VALUE = "jump";

    /**
     * 加载天气时显示的信息
     */
    public static final String PROVINCE_LIST_TITLE="中国";
    public static final String PROGRESS_MESSAGE="正在加载...";
    public static final String ERROR_MESSAGE="加载失败";

    /**
     * okhttp head 的name
     */
    public static final String OKHTTP_HEAD_NAME="okhttp_head_name";

    /**
     * 获取地理位置api的head的key
     */
    public static final String LOCATION_HEAD_KEY="location";

    /**
     * 获取天气信息api的head的key
     */
    public static final String WEATHER_HEAD_KEY="weather";

    /**
     * 天气api返回成功的标志
     */
    public static final String WEATHER_STATUE_SUCCESS = "ok";

    /**
     * 从xml读取字符失败返回的默认值
     */
    public static final String XML_DEFAULT_VALUE = null;

    /**
     * 地理位置api的baseurl
     */
    public static final String LOCATION_BASE_URL="http://guolin.tech/";

    /**
     * 天气api的baseurl
     */
    public static final String WEATHER_BASE_URL ="https://free-api.heweather.net/";

    /**
     * 天气api的key
     */
    public static final String WEATHER_USER_KEY = "bf843e92395349b1896c86069bc0b872";

    /**
     * okhttp连接超时时间
     */
    public static final int OKHTTP_CONNECT_TIME_OUT=5;

    /**
     * 不同模块更新失败显示的信息
     */
    public static final String FORECAST_ERROR_MESSAGE = "更新未来七天天气信息失败";
    public static final String NOW_ERROR_MESSAGE = "更新当前天气信息失败";
    public static final String SUGGESTION_ERROR_MESSAGE = "更新生活建议信息失败";
    public static final String AQI_ERROR_MESSAGE = "更新AQI信息失败";
    public static final String IMAGE_ERROR_MESSAGE = "更新图片失败";
}
