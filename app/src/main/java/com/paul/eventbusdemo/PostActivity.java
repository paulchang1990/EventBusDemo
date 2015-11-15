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
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class PostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_bg)
    void postBg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                postMsg(new EventMessage("From a background thread", threadName));
                finish();
            }
        }).start();
    }

    @OnClick(R.id.btn_main)
    void postMain() {
        String threadName = Thread.currentThread().getName();
        postMsg(new EventMessage("From a main thread", threadName));
        finish();
    }

    private void postMsg(EventMessage msg) {
        EventBus.getDefault().post(msg);
    }
}
