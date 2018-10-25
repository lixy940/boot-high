<p>1.多数据的使用参考OrderServiceImpl，OrderDetailServiceImpl</p>
<p>2.默认数据源为参考StudentServiceImpl</p>
<p>3.线程启动使用参考InitStartUp，ExcelSynThread，DataInsertThread</p>
<p>4.MvcInterceptorConfig配置拦截器</p>
<p>5.SystemLogAspect进行aop的控制</p>
<p>6.WebConfig对过滤器和监听器的servlet注入</p>
<p>7.数据库部分在resource/db目录下</p>
<p>8.swagger进行单元测试，通过访问http://localhost:port/swagger-ui.html</p>
<p>登录拦截会拦截所有，需要对swagger放行，在MvcInterceptorConfig类</p>
<p>.excludePathPatterns("/swagger-resources/**")</p>
<p>.excludePathPatterns("/v2/api-docs")</p>
<p>.excludePathPatterns("/webjars/springfox-swagger-ui/**");</p>
<p>9.webSocket长连接使用，主要涉及的类有WebSocketConfig、WebSocketEndPoint、WebSocketController及websocket.jsp</p>
<p>1）WebSocketController中的receive1()、receive2()用于模拟演示服务器端给客户端用户发送消息，如</p>
<p>String message = "上传任务完成了！|用户1";这里将消息和接收消息的用户通过|进行分割。</p>
<p>2）这里的用户主要是登录时存储在session中的用户，所有需要先登录，保证session中LoginUserVO有userId值。然后可以通过在websocket.jsp页面的发送消息框进行消息的发送，</p>
<p>并且输入接收人</p>
<p>3）实际项目中，前端提交一个大的文件上传后，需要后天进行长时间的处理时，前端提交任务后后端采用异步方式进行任务处理，</p>
<p>处理完成后可通过websocket将消息推送到前端，然后弹框提醒，如：MonitorTaskHandlerThread，</p>
<p>  endPoint.sendToUser("task is done！|用户1");此处需要用|进行分割消息内容和接收人</p>
<p>10.增加TimeBeginToEndExecuteCalUtils类对时间的开始和结束进行计算，且保证当前线程下执行的时间计算</p>
<p>11. 增加管理员和超级管理员的注解控制，对某些方法进行注解后，只有是管理员否则超级管理员的才可以访问该方法
详情如：PermessionLimit和PermissionInterceptor及MvcInterceptorConfig类
<p/>
<p>12.登录或登出，进行cookie控制，如CookieUtil及LoginController<p/>
<p>13.增加ftp逻辑，FtpUtil类和ExcelImportController类可参看调用，注意上传的文件名不支出中文<p/>
