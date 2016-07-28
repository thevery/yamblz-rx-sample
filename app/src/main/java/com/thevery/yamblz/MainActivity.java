package com.thevery.yamblz;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.thevery.yamblz.api.YandexTranslateApi;
import com.thevery.yamblz.api.YandexTranslateResponse;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private YandexTranslateApi translateApi;
    private TextView textView;

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
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl("https://translate.yandex.net/api/v1.5/tr.json/")
                .client(retrofitClientBuilder.build())
                .build();

        translateApi = retrofit.create(YandexTranslateApi.class);
        textView = (TextView) findViewById(R.id.tv);
    }

    public void translate(View view) {
        final String text = ((EditText) findViewById(R.id.et)).getText().toString();
        startTranslation(text);
    }

//    @NonNull
//    private String translate(@NonNull String source) {
//        return translateApi.translate(YandexTranslateApi.YANDEX_API_KEY, source, "ru").toBlocking().value().text[0];
//    }

    private void startTranslation(@NonNull final String source) {
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... strings) {
                final Response<YandexTranslateResponse> response;
                try {
                    return getResult(source);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "fail :(";
                }
            }

            @Override
            protected void onPostExecute(String s) {
                textView.setText(s);
            }
        }.execute(source);
    }

    private String getResult(@NonNull String source) throws IOException {
        Response<YandexTranslateResponse> response;
        response = translateApi
                .translateCall(YandexTranslateApi.YANDEX_API_KEY, source, "ru")
                .execute();
        return response.body().text[0];
    }
}
