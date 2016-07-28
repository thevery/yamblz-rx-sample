package com.thevery.yamblz;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void translate(View view) {
        final String text = ((EditText) findViewById(R.id.et)).getText().toString();
        ((TextView) findViewById(R.id.tv)).setText(translate(text));
    }

    @NonNull
    private String translate(@NonNull String source) {
        return source.toUpperCase();
    }
}
