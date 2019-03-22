package com.xiaogege.jerry.view;

import android.graphics.Bitmap;

import com.xiaogege.jerry.model.database.City;
import com.xiaogege.jerry.model.database.County;
import com.xiaogege.jerry.model.database.Province;
import com.xiaogege.jerry.model.xml.AQI;
import com.xiaogege.jerry.model.xml.Forecast;
import com.xiaogege.jerry.model.xml.Suggestion;
import com.xiaogege.jerry.model.xml.Now;

import java.util.List;

/**
 * ui接口集合
 */
public class ViewContract {

    public interface BaseView{
        void showToast(String message);
        void showProgress();
        void showError();
    }

    public interface MainActivityView extends BaseView{

    }

    public interface WeatherActivityView extends BaseView{
        void showNow(Now now);
        void showForecast(Forecast forecast);
        void showAQI(AQI aqi);
        void showSuggestion(Suggestion suggestion);
        void showImage(Bitmap image);
        void showError(String message);
    }

    public interface WeatherFragmentView extends BaseView{
        void showProvinceList(List<Province> provinceList);
        void showCityList(List<City> cityList);
        void showCountyList(List<County> countyList);
    }
}
