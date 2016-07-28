package com.thevery.yamblz.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * API interface for https://tech.yandex.ru/translate/doc/dg/reference/translate-docpage/
 */
public interface YandexTranslateApi {
    String YANDEX_API_KEY = "trnsl.1.1.20160728T140517Z.5cc52d9c03643fb3.90d08d77a81e382c545d2cd5619babd370d5b52f";

    @GET("translate")
    Observable<YandexTranslateResponse> translateRx(
            @Query("key") String key,
            @Query("text") String text,
            @Query("lang") String lang
    );

    @GET("translate")
    Call<YandexTranslateResponse> translateCall(
            @Query("key") String key,
            @Query("text") String text,
            @Query("lang") String lang
    );

}
