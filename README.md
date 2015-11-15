# EventBusDemo
### EventBus简介
#### 1. 简介
    EventBus可以实现Android程序中各个组件或线程之间的交流。类似于一个观察者模式的实现，发送者和订阅者高度解耦，代码更加
    简洁易用，源码实现主要用到了反射技术。
    
#### 2. 参考
- EventBus源代码：[greenrobot/EventBus](https://github.com/greenrobot/EventBus)
- HowTo：[EventBus How-To](https://github.com/greenrobot/EventBus/blob/master/HOWTO.md)
- 源码分析：[EventBus源码分析](http://a.codekk.com/detail/Android/Trinea/EventBus%20%E6%BA%90%E7%A0%81%E8%A7%A3%E6%9E%90)

#### 3. 简单使用
1. 首先确定要被传递的数据：可以是任何一个没有特殊约定的简单java对象，如自定义一个EventMessage
2. 订阅者进行数据订阅
  - 在订阅者的合适位置使用`EventBus.getDefault().register(this)`进行订阅
  - 根据需要（见Demo分析说明结果）在订阅者类中定义特定格式的方法，如`public void onEvent(EventMessage msg){}`
  - 在订阅者的合适位置使用`EventBus.getDefault().unregister(this)`取消订阅
3. 发送者中发送该数据：使用`EventBus.getDefault().post(msg)`进行发送

#### 4. 复杂使用
- 定义订阅者接收到发送者数据的优先级(默认为0)：如`int priority = 2; EventBus.getDefault().register(this,priority)`，只有在
订阅者有相同的订阅方法中时有可比性，如都使用了默认的线程处理模式`onEvent()`
- 当订阅者想获得在其订阅之前发送者最后发送的或发送者最后缓存到的数据（因为最后发送的数据有可能被移除掉了） ，方法名和顺序与简单使用略有不同：
  - 发送者发送数据时使用`EventBus.getDefault().postSticky(EventMessage msg)`
  - 订阅者订阅时使用`EventBus.getDefault().registerSticky(this)`
  - 除了可以在几个特定方法中获取到发送者最后缓存数据外，还可以直接使用`EventBus.getDefault().getStickyEvent(Class<?> eventType)`获得该数据
  
#### 5. 混淆避免
  ```
 -keepclassmembers class ** {
    public void onEvent*(***);
}

# Only required if you use AsyncExecutor
-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
```

### 示例分析
#### 1. Demo截屏
<img src="https://github.com/paulchang1990/EventBusDemo/blob/master/captures/event_bus_screen_shot.gif" width = "300" height = "500" alt="screen shot" align=center />
#### 2. Demo分析说明
从Demo中可以看出在主线程或子线程执行post方法发送数据时：
- 订阅者的`onEvent()`方法的执行线程为发送者post数据的线程
- 订阅者的`onEventMainThread()`方法执行线程为主线程
- 订阅者的`onEventBackgroudThread()`方法执行线程分为两种情况：
  - 发送者为主线程，则所有该方法都在唯一的一个独立的子线程中执行，所以要避免该线程被阻塞
  - 发送者为子线程，则该方法直接在发送时的子线程中执行
- 订阅者的`onEventAsync()`方法执行线程为非post数据的子线程

> 通过阅读源代码也可以证实以上的分析结果

## License
```
Copyright 2015 Paul Chang

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
