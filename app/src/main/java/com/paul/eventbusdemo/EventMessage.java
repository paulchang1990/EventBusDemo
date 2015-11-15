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

/**
 * Created by Paul Chang on 2015/11/15.
 */
public class EventMessage {
    private String name;
    private String threadName;

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public EventMessage() {
    }

    public EventMessage(String name, String threadName) {
        this.name = name;
        this.threadName = threadName;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "name='" + name + '\'' +
                ", threadName='" + threadName + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
