/*
 * Copyright 2015 Paul Chang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paul.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Paul Chang on 2015/11/15.
 */
public class SubscriberActivity extends AppCompatActivity {

    @Bind(R.id.tv_default)
    TextView tvDefault;
    @Bind(R.id.tv_main)
    TextView tvMain;
    @Bind(R.id.tv_bg)
    TextView tvBg;
    @Bind(R.id.tv_async)
    TextView tvAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        ButterKnife.bind(this);
    }

    private void clearAllText() {
        tvDefault.setText("");
        tvMain.setText("");
        tvBg.setText("");
        tvAsync.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @OnClick(R.id.btn_go_post)
    void goPostAct() {
        clearAllText();
        Intent intent = new Intent(this, PostActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(EventMessage msg) {
        final String content = "CurrentMethod=onEvent\r\nFromThread=" + msg.getThreadName() +
                "\r\nCurrentThread=" + Thread.currentThread().getName();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvDefault.setText(content);
            }
        });
    }

    public void onEventMainThread(EventMessage msg) {
        final String content = "CurrentMethod=onEventMainThread\r\nFromThread=" + msg
                .getThreadName() +
                "\r\nCurrentThread=" + Thread.currentThread().getName();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvMain.setText(content);
            }
        });
    }

    public void onEventBackgroundThread(final EventMessage msg) {
        final String content = "CurrentMethod=onEventBackgroundThread\r\nFromThread=" + msg
                .getThreadName() + "\r\nCurrentThread=" + Thread.currentThread().getName();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvBg.setText(content);
            }
        });
    }

    public void onEventAsync(final EventMessage msg) {
        final String content = "CurrentMethod=onEventAsync\r\nFromThread=" + msg.getThreadName() +
                "\r\nCurrentThread=" + Thread.currentThread().getName();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvAsync.setText(content);
            }
        });
    }
}
