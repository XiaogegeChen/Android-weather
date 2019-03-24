package com.xiaogege.jerry.view.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaogege.jerry.R;
import com.xiaogege.jerry.model.database.City;
import com.xiaogege.jerry.model.database.County;
import com.xiaogege.jerry.model.database.Province;
import com.xiaogege.jerry.presenter.WeatherFragmentPresenterImpl;
import com.xiaogege.jerry.util.LogUtils;
import com.xiaogege.jerry.util.ToastUtils;
import com.xiaogege.jerry.util.XmlIOUtils;
import com.xiaogege.jerry.view.ViewContract;
import com.xiaogege.jerry.view.activity.MainActivity;
import com.xiaogege.jerry.view.activity.WeatherActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

import static com.xiaogege.jerry.model.Constants.ERROR_MESSAGE;
import static com.xiaogege.jerry.model.Constants.PROGRESS_MESSAGE;
import static com.xiaogege.jerry.model.Constants.PROVINCE_LIST_TITLE;
import static com.xiaogege.jerry.model.Constants.SELECTED_LOCATION_KEY;

public class WeatherFragment extends Fragment implements ViewContract.WeatherFragmentView {

    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY = 2;
    private static final String TAG = "WeatherFragment";

    @BindView(R.id.list_view)
    ListView mListView;

    @BindView (R.id.title_text)
    TextView mArea;

    @BindView (R.id.back_button)
    Button mBack;

    private List<String> mDataList;

    private List<Province> mProvinceList;

    private List<City> mCityList;

    private List<County> mCountyList;

    private Province mSelectedProvince;

    private City mSelectedCity;

    private County mSelectedCounty;

    private ArrayAdapter<String> mAdapter;

    private ProgressDialog mProgressDialog;

    private WeatherFragmentPresenterImpl mWeatherFragmentPresenter;

    private int mCurrentLevel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.choose_area, container, false);
        mWeatherFragmentPresenter = new WeatherFragmentPresenterImpl ();
        mWeatherFragmentPresenter.attach (this);
        ButterKnife.bind (this,view);
        mDataList = new ArrayList<> ();
        mAdapter = new ArrayAdapter<> (getContext (), android.R.layout.simple_list_item_1, mDataList);
        mListView.setAdapter (mAdapter);
        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated (savedInstanceState);

        //列表条目点击事件
        mListView.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                switch (mCurrentLevel){
                    case LEVEL_PROVINCE:
                        if(mProvinceList != null && mProvinceList.size () > 0){
                            mSelectedProvince = mProvinceList.get (position);
                            mWeatherFragmentPresenter.queryCity (mSelectedProvince.getProvinceId ());
                        }
                        break;
                    case LEVEL_CITY:
                        //进行判空,当加载失败的时候mCityList是空的。
                        if(mCityList != null && mCityList.size () > 0){
                            mSelectedCity = mCityList.get (position);
                            mWeatherFragmentPresenter.queryCounty (mSelectedCity.getCityId ());
                        }
                        break;
                    case LEVEL_COUNTY:
                        //进行判空,当加载失败的时候mCountyList是空的。
                        if(mCountyList != null && mCountyList.size () > 0){
                            mSelectedCounty = mCountyList.get (position);

                            // 拿到城市代码
                            String weatherId = mSelectedCounty.getWeatherId ();
                            LogUtils.d (TAG, mSelectedCounty.getCountyName ()+mSelectedCounty.getWeatherId ());

                            //将拿到的城市代码存储
                            XmlIOUtils.xmlPut (SELECTED_LOCATION_KEY, weatherId, getContext ());

                            // 在MainActivity就跳转,在WeatherActivity就调WeatherActivity中的方法
                            if(getActivity () instanceof MainActivity){
                                WeatherActivity.start (getContext (), WeatherActivity.class, weatherId);
                            }else if(getActivity () instanceof WeatherActivity){
                                ((WeatherActivity) getActivity ()).queryFromDrawers (weatherId);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        //返回键点击
        mBack.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                switch (mCurrentLevel){
                    case LEVEL_COUNTY:

                        //设置数据为上一级的数据
                        mDataList.clear ();
                        for(City city:mCityList){
                            mDataList.add (city.getCityName ());
                        }
                        refreshList ();

                        //设置级别为上一级
                        mCurrentLevel = LEVEL_CITY;

                        //改变标题
                        mArea.setText (mSelectedProvince.getProvinceName ());
                        break;
                    case LEVEL_CITY:
                        mDataList.clear ();
                        for(Province province:mProvinceList){
                            mDataList.add (province.getProvinceName ());
                        }
                        refreshList ();
                        mCurrentLevel = LEVEL_PROVINCE;
                        mArea.setText (PROVINCE_LIST_TITLE);
                        mBack.setVisibility (View.INVISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });

        //首先查询省列表
        mWeatherFragmentPresenter.queryProvince ();
        mCurrentLevel = LEVEL_PROVINCE;
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        mWeatherFragmentPresenter.detach ();
    }

    @Override
    public void showToast(String message) {
        ToastUtils.showToast (getContext (), message);
    }

    @Override
    public void showProgress() {
        if(mProgressDialog != null){
            mProgressDialog.show ();
        }else{
            mProgressDialog = new ProgressDialog (getActivity ());
            mProgressDialog.setMessage (PROGRESS_MESSAGE);
            mProgressDialog.setCanceledOnTouchOutside (false);
            mProgressDialog.show ();
        }
    }

    @Override
    public void showError() {
        hideProgressDialog();
        showToast (ERROR_MESSAGE);
    }

    @SuppressLint("CheckResult")
    @Override
    public void showProvinceList(List<Province> provinceList) {
        //隐藏进度条
        hideProgressDialog ();
        mArea.setText (PROVINCE_LIST_TITLE);
        mBack.setVisibility (View.INVISIBLE);
        Observable.just (provinceList)
                .filter (new Predicate<List<Province>> () {
                    @Override
                    public boolean test(List<Province> provinces) throws Exception {
                        return provinces.size () > 0;
                    }
                })
                .flatMap (new Function<List<Province>, ObservableSource<List<String>>> () {
                    @Override
                    public ObservableSource<List<String>> apply(List<Province> provinces) throws Exception {
                        mDataList.clear ();
                        for(Province province:provinces){
                            mDataList.add (province.getProvinceName ());
                        }
                        mProvinceList = new ArrayList<> ();
                        mProvinceList.addAll (provinces);
                        return Observable.just (mDataList);
                    }
                })
                .subscribe (new Consumer<List<String>> () {
                    @Override
                    public void accept(List<String> dataList) throws Exception {
                        refreshList();
                    }
                }, new Consumer<Throwable> () {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace ();
                    }
                });
        //设置当前的级别为省
        mCurrentLevel = LEVEL_PROVINCE;
    }

    @SuppressLint("CheckResult")
    @Override
    public void showCityList(List<City> cityList) {
        hideProgressDialog ();
        mBack.setVisibility (View.VISIBLE);
        mArea.setText (mSelectedProvince.getProvinceName ());
        Observable.just (cityList)
                .filter (new Predicate<List<City>> () {
                    @Override
                    public boolean test(List<City> cityList) throws Exception {
                        return cityList.size () > 0;
                    }
                })
                .flatMap (new Function<List<City>, ObservableSource<List<String>>> () {
                    @Override
                    public ObservableSource<List<String>> apply(List<City> cityList) throws Exception {
                        mDataList.clear ();
                        for(City city:cityList){
                            mDataList.add (city.getCityName ());
                        }
                        mCityList = new ArrayList<> ();
                        mCityList.addAll (cityList);
                        return Observable.just (mDataList);
                    }
                })
                .subscribe (new Consumer<List<String>> () {
                    @Override
                    public void accept(List<String> dataList) throws Exception {
                        refreshList();
                    }
                }, new Consumer<Throwable> () {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace ();
                    }
                });

        //设置当前的级别为市
        mCurrentLevel = LEVEL_CITY;
    }

    @SuppressLint("CheckResult")
    @Override
    public void showCountyList(List<County> countyList) {
        hideProgressDialog ();
        mBack.setVisibility (View.VISIBLE);
        mArea.setText (mSelectedCity.getCityName ());
        Observable.just (countyList)
                .filter (new Predicate<List<County>> () {
                    @Override
                    public boolean test(List<County> counties) throws Exception {
                        return counties.size () > 0;
                    }
                })
                .flatMap (new Function<List<County>, ObservableSource<List<String>>> () {
                    @Override
                    public ObservableSource<List<String>> apply(List<County> counties) throws Exception {
                        mDataList.clear ();
                        for(County county:counties){
                            mDataList.add (county.getCountyName ());
                        }
                        mCountyList = new ArrayList<> ();
                        mCountyList.addAll (counties);
                        return Observable.just (mDataList);
                    }
                })
                .subscribe (new Consumer<List<String>> () {
                    @Override
                    public void accept(List<String> stringList) throws Exception {
                        refreshList();
                    }
                }, new Consumer<Throwable> () {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace ();
                    }
                });
        //设置当前的级别为县
        mCurrentLevel = LEVEL_COUNTY;
    }

    private void hideProgressDialog(){
        if(mProgressDialog != null){
            mProgressDialog.dismiss ();
        }
    }

    private void refreshList(){
        mAdapter.notifyDataSetChanged ();
        mListView.setSelection (0);
    }
}
