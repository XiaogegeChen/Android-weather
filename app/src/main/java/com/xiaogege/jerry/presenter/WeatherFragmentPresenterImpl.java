package com.xiaogege.jerry.presenter;

import android.annotation.SuppressLint;

import com.xiaogege.jerry.model.database.City;
import com.xiaogege.jerry.model.database.County;
import com.xiaogege.jerry.model.gson.Province;
import com.xiaogege.jerry.util.helper.QueryDataHelper;
import com.xiaogege.jerry.util.manager.RetrofitManager;
import com.xiaogege.jerry.view.ViewContract;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 *  天气fragment的业务逻辑
 */
public class WeatherFragmentPresenterImpl implements PresenterContract.WeatherFragmentPresenter {

    /**
     * 天气fragment的视图
     */
    private ViewContract.WeatherFragmentView mWeatherFragmentView;

    private String mProvinceId;

    private String mCityId;

    @Override
    public void attach(ViewContract.WeatherFragmentView weatherFragmentView) {
        mWeatherFragmentView = weatherFragmentView;
    }

    @Override
    public void detach() {
        mWeatherFragmentView = null;
    }

    @SuppressLint("CheckResult")
    @Override
    public List<com.xiaogege.jerry.model.database.Province> queryProvince(){
        final List<com.xiaogege.jerry.model.database.Province> provinceList = new ArrayList<> ();
        mWeatherFragmentView.showProgress ();
        new QueryProvinceHelper ().queryData (QueryDataHelper.Type.NORMAL)
                .subscribe (new Consumer<List<com.xiaogege.jerry.model.database.Province>> () {
                    @Override
                    public void accept(List<com.xiaogege.jerry.model.database.Province> provinces) throws Exception {
                        mWeatherFragmentView.showProvinceList (provinces);
                        provinceList.addAll (provinces);
                    }
                }, new Consumer<Throwable> () {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace ();
                        mWeatherFragmentView.showError ();
                    }
                });
        return provinceList;
    }

    @SuppressLint("CheckResult")
    @Override
    public List<com.xiaogege.jerry.model.database.City> queryCity(String provinceId){
        mProvinceId = provinceId;
        final List<com.xiaogege.jerry.model.database.City> cityList = new ArrayList<> ();
        mWeatherFragmentView.showProgress ();
        new QueryCityHelper ().queryData (QueryDataHelper.Type.NORMAL)
                .subscribe (new Consumer<List<City>> () {
                    @Override
                    public void accept(List<City> cities) throws Exception {
                        mWeatherFragmentView.showCityList (cities);
                        cityList.addAll (cities);
                    }
                }, new Consumer<Throwable> () {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace ();
                        mWeatherFragmentView.showError ();
                    }
                });
        return cityList;
    }

    @SuppressLint("CheckResult")
    @Override
    public List<com.xiaogege.jerry.model.database.County> queryCounty(String cityId){
        mCityId = cityId;
        final List<com.xiaogege.jerry.model.database.County> countyList = new ArrayList<> ();
        mWeatherFragmentView.showProgress ();
        new QueryCountyHelper ().queryData (QueryDataHelper.Type.NORMAL)
                .subscribe (new Consumer<List<County>> () {
                    @Override
                    public void accept(List<County> counties) throws Exception {
                        mWeatherFragmentView.showCountyList (counties);
                        countyList.addAll (counties);
                    }
                }, new Consumer<Throwable> () {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace ();
                        mWeatherFragmentView.showError ();
                    }
                });
        return countyList;
    }

    /**
     * 处理省数据的类
     */
    class QueryProvinceHelper extends QueryDataHelper<List<com.xiaogege.jerry.model.database.Province>>{

        @Override
        public Observable<List<com.xiaogege.jerry.model.database.Province>> queryFromNet() {
            return RetrofitManager.getInstance ()
                    .create ()
                    .queryProvince ()
                    .filter (new Predicate<List<Province>> () {
                        @Override
                        public boolean test(List<Province> provinces) throws Exception {
                            return provinces != null && provinces.size ()>0;
                        }
                    })
                    .flatMap (new Function<List<Province>, ObservableSource<List<com.xiaogege.jerry.model.database.Province>>> () {
                        @Override
                        public ObservableSource<List<com.xiaogege.jerry.model.database.Province>> apply(List<Province> provinces) throws Exception {
                            //建表
                            LitePal.getDatabase ();
                            //写入数据库
                            List<com.xiaogege.jerry.model.database.Province> dbProvinceList=new ArrayList<> ();
                            for(Province province:provinces){
                                com.xiaogege.jerry.model.database.Province p = new com.xiaogege.jerry.model.database.Province ();
                                p.setProvinceId (province.getId ());
                                p.setProvinceName (province.getName ());
                                p.save ();
                                dbProvinceList.add (p);
                            }
                            return Observable.just (dbProvinceList);
                        }
                    });
        }

        @Override
        public Observable<List<com.xiaogege.jerry.model.database.Province>> queryFromLocal() {
            return Observable.create (new ObservableOnSubscribe<List<com.xiaogege.jerry.model.database.Province>> () {
                @Override
                public void subscribe(ObservableEmitter<List<com.xiaogege.jerry.model.database.Province>> emitter) throws Exception {
                    List<com.xiaogege.jerry.model.database.Province> provinceList = LitePal.findAll (com.xiaogege.jerry.model.database.Province.class);
                    if(provinceList.size ()>0){
                        emitter.onNext (provinceList);
                    }
                    emitter.onComplete ();
                }
            });
        }
    }

    /**
     * 处理市数据的类
     */
    class QueryCityHelper extends QueryDataHelper<List<City>>{

        @Override
        public Observable<List<City>> queryFromNet() {
            return RetrofitManager.getInstance ()
                    .create ()
                    .queryCity (mProvinceId)
                    .filter (new Predicate<List<com.xiaogege.jerry.model.gson.City>> () {
                        @Override
                        public boolean test(List<com.xiaogege.jerry.model.gson.City> cities) throws Exception {
                            return cities != null && cities.size ()>0;
                        }
                    })
                    .flatMap (new Function<List<com.xiaogege.jerry.model.gson.City>, ObservableSource<List<City>>> () {
                        @Override
                        public ObservableSource<List<City>> apply(List<com.xiaogege.jerry.model.gson.City> cities) throws Exception {
                            List<City> cityList = new ArrayList<> ();
                            for(com.xiaogege.jerry.model.gson.City city:cities){
                                City c = new City ();
                                c.setCityId (city.getId ());
                                c.setCityName (city.getName ());
                                c.setProvinceId (mProvinceId);
                                c.save ();
                                cityList.add (c);
                            }
                            return Observable.just (cityList);
                        }
                    });
        }

        @Override
        public Observable<List<City>> queryFromLocal() {
            return Observable.create (new ObservableOnSubscribe<List<City>> () {
                @Override
                public void subscribe(ObservableEmitter<List<City>> emitter) throws Exception {
                    List<City> cityList = LitePal.where ("provinceId like ?", mProvinceId).find (City.class);
                    if(cityList.size ()>0){
                        emitter.onNext (cityList);
                    }
                    emitter.onComplete ();
                }
            });
        }
    }

    /**
     *  处理县数据的类
     */
    class QueryCountyHelper extends QueryDataHelper<List<County>>{

        @Override
        public Observable<List<County>> queryFromNet() {
            return RetrofitManager.getInstance ()
                    .create ()
                    .queryCounty (mProvinceId, mCityId)
                    .filter (new Predicate<List<com.xiaogege.jerry.model.gson.County>> () {
                        @Override
                        public boolean test(List<com.xiaogege.jerry.model.gson.County> counties) throws Exception {
                            return counties != null && counties.size () > 0;
                        }
                    })
                    .flatMap (new Function<List<com.xiaogege.jerry.model.gson.County>, ObservableSource<List<County>>> () {
                        @Override
                        public ObservableSource<List<County>> apply(List<com.xiaogege.jerry.model.gson.County> counties) throws Exception {
                            List<County> countyList = new ArrayList<> ();
                            for(com.xiaogege.jerry.model.gson.County county:counties){
                                County c = new County ();
                                c.setCountyId (county.getId ());
                                c.setCountyName (county.getName ());
                                c.setCityId (mCityId);
                                c.setWeatherId (county.getWeather_id ());
                                c.save ();
                                countyList.add (c);
                            }
                            return Observable.just (countyList);
                        }
                    });
        }

        @Override
        public Observable<List<County>> queryFromLocal() {
            return Observable.create (new ObservableOnSubscribe<List<County>> () {
                @Override
                public void subscribe(ObservableEmitter<List<County>> emitter) throws Exception {
                    List<County> countyList = LitePal.where ("cityId like ?", mCityId).find (County.class);
                    if(countyList.size () > 0){
                        emitter.onNext (countyList);
                    }
                    emitter.onComplete ();
                }
            });
        }
    }
}
