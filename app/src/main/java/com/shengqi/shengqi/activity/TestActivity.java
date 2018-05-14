package com.shengqi.shengqi.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shengqi.shengqi.R;

/**
 * Created by Administrator on 2018/5/10.
 */

public class TestActivity extends AppCompatActivity {

    private LinearLayout eL;
    private LinearLayout lL;
    private TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        eL=findViewById(R.id.error_layout);
        lL=findViewById(R.id.load_layout);
        tv=findViewById(R.id.textdata);
        eL.setVisibility(View.VISIBLE);
        lL.setVisibility(View.GONE);
        tv.setVisibility(View.GONE);

    }
}
