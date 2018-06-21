1.多数据的使用参考OrderServiceImpl，OrderDetailServiceImpl
2.默认数据源为参考StudentServiceImpl
3.线程启动使用参考InitStartUp，ExcelSynThread，DataInsertThread
4.MvcInterceptorConfig配置拦截器
5.SystemLogAspect进行aop的控制
6.WebConfig对过滤器和监听器的servlet注入
7.数据库部分在resource/db目录下
8.swagger进行单元测试，通过访问http://localhost:port/swagger-ui.html
登录拦截会拦截所有，需要对swagger放行，在MvcInterceptorConfig类
.excludePathPatterns("/swagger-resources/**")
.excludePathPatterns("/v2/api-docs")
.excludePathPatterns("/webjars/springfox-swagger-ui/**");
9.webSocket长连接使用，主要涉及的类有WebSocketConfig、WebSocketEndPoint、WebSocketController及websocket.jsp
1）WebSocketController中的receive1()、receive2()用于模拟演示服务器端给客户端用户发送消息，如
String message = "上传任务完成了！|用户1";这里将消息和接收消息的用户通过|进行分割。
2）这里的用户主要是登录时存储在session中的用户，所有需要先登录，保证session中LoginUserVO有userId值。然后可以通过在websocket.jsp页面的发送消息框进行消息的发送，
并且输入接收人
3）实际项目中，前端提交一个大的文件上传后，需要后天进行长时间的处理时，前端提交任务后后端采用异步方式进行任务处理，
处理完成后可通过websocket将消息推送到前端，然后弹框提醒，如：MonitorTaskHandlerThread，
  endPoint.sendToUser("task is done！|用户1");此处需要用|进行分割消息内容和接收人
