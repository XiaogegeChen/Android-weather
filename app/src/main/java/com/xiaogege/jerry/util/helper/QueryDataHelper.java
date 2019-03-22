package com.xiaogege.jerry.util.helper;

import android.annotation.SuppressLint;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class QueryDataHelper<T> {

    @SuppressLint("CheckResult")
    public Maybe<T> queryData(Type type){
        switch (type){
            case NORMAL:
                return Observable.concat (queryFromLocal (),queryFromNet ())
                        .firstElement ()
                        .subscribeOn (Schedulers.io ())
                        .observeOn (AndroidSchedulers.mainThread ());
            case REFRESH:
                return Observable.concat (queryFromNet (), queryFromLocal ())
                        .firstElement ()
                        .subscribeOn (Schedulers.io ())
                        .observeOn (AndroidSchedulers.mainThread ());
            default:
                return Observable.concat (queryFromNet (), queryFromLocal ())
                        .firstElement ()
                        .subscribeOn (Schedulers.io ())
                        .observeOn (AndroidSchedulers.mainThread ());
        }
    }

    public abstract Observable<T> queryFromNet();
    public abstract Observable<T> queryFromLocal();

    /**
     * 访问类型
     */
    public enum Type{
        /**
         * 正常的访问类型，优先从本地拿数据，比如读取地理位置的列表
         */
        NORMAL,

        /**
         * 刷新式的访问类型，优先请求网络拿数据，比如刷新界面
         */
        REFRESH
    }
}
