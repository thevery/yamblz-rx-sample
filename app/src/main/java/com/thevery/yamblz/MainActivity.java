package com.thevery.yamblz;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.thevery.yamblz.api.YandexTranslateApi;
import com.thevery.yamblz.api.YandexTranslateReponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private YandexTranslateApi translateApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final OkHttpClient client = new OkHttpClient();
        final OkHttpClient.Builder retrofitClientBuilder = client.newBuilder();

        if (BuildConfig.DEBUG) {
            final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            retrofitClientBuilder.addInterceptor(interceptor);
            retrofitClientBuilder.addNetworkInterceptor(interceptor);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl("https://translate.yandex.net/api/v1.5/tr.json/")
                .client(retrofitClientBuilder.build())
                .build();

        translateApi = retrofit.create(YandexTranslateApi.class);
    }

    public void translate(View view) {
        final String text = ((EditText) findViewById(R.id.et)).getText().toString();
        startTranslation(text);
//        ((TextView) findViewById(R.id.tv)).setText(translate(text));
    }

//    @NonNull
//    private String translate(@NonNull String source) {
//        return translateApi.translate(YandexTranslateApi.YANDEX_API_KEY, source, "ru").toBlocking().value().text[0];
//    }

    private Subscription startTranslation(@NonNull String source) {
        return translateApi.translate(YandexTranslateApi.YANDEX_API_KEY, source, "ru")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<YandexTranslateReponse, String>() {
                    @Override
                    public String call(YandexTranslateReponse yandexTranslateReponse) {
                        return yandexTranslateReponse.text[0];
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        ((TextView) findViewById(R.id.tv)).setText(s);
                    }
                });
    }
}
