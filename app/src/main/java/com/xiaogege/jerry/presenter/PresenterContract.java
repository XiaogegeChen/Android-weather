package com.xiaogege.jerry.presenter;

import com.xiaogege.jerry.model.database.City;
import com.xiaogege.jerry.model.database.County;
import com.xiaogege.jerry.model.database.Province;
import com.xiaogege.jerry.view.ViewContract;

import java.util.List;

public class PresenterContract {

    public interface BasePresenter<T extends ViewContract.BaseView>{
        void attach(T t);
        void detach();
    }

    public interface MainActivityPresenter extends BasePresenter<ViewContract.MainActivityView>{

    }

    public interface WeatherActivityPresenter extends BasePresenter<ViewContract.WeatherActivityView>{
        void checkStatus();
        void queryAll();
        void refresh();
    }

    public interface WeatherFragmentPresenter extends BasePresenter<ViewContract.WeatherFragmentView>{
        List<Province> queryProvince();
        List<City> queryCity(String provinceId);
        List<County> queryCounty(String cityId);
    }
}
