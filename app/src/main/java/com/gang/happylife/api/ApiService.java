package com.gang.happylife.api;

import com.gang.happylife.base.basebean.ApiResponseWraperNoData;
import com.gang.happylife.base.baseparams.RequestParam;
import com.gang.happylife.bean.TextJokeBean;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/5.
 */

public interface ApiService {

    @GET("randJoke.php")
    Observable<ApiResponseWraperNoData<TextJokeBean>> getTextJoke(@QueryMap RequestParam param);
}
