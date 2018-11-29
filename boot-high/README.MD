####  1.多数据的使用参考OrderServiceImpl，OrderDetailServiceImpl</p>
####  2.默认数据源为参考StudentServiceImpl</p>
####  3.线程启动使用参考InitStartUp，ExcelSynThread，DataInsertThread</p>
####  4.MvcInterceptorConfig配置拦截器</p>
####  5.SystemLogAspect进行aop的控制</p>
####  6.WebConfig对过滤器和监听器的servlet注入</p>
####  7.数据库部分在resource/db目录下</p>
####  8.swagger进行单元测试，通过访问http://localhost:port/swagger-ui.html</p>
####  登录拦截会拦截所有，需要对swagger放行，在MvcInterceptorConfig类</p>
####  .excludePathPatterns("/swagger-resources/**")</p>
####  .excludePathPatterns("/v2/api-docs")</p>
####  .excludePathPatterns("/webjars/springfox-swagger-ui/**");</p>
####  9.webSocket长连接使用，主要涉及的类有WebSocketConfig、WebSocketEndPoint、WebSocketController及websocket.jsp</p>
####  1）WebSocketController中的receive1()、receive2()用于模拟演示服务器端给客户端用户发送消息，如</p>
####  String message = "上传任务完成了！|用户1";这里将消息和接收消息的用户通过|进行分割。</p>
####  2）这里的用户主要是登录时存储在session中的用户，所有需要先登录，保证session中LoginUserVO有userId值。然后可以通过在websocket.jsp页面的发送消息框进行消息的发送，</p>
####  并且输入接收人</p>
####  3）实际项目中，前端提交一个大的文件上传后，需要后天进行长时间的处理时，前端提交任务后后端采用异步方式进行任务处理，</p>
####  处理完成后可通过websocket将消息推送到前端，然后弹框提醒，如：MonitorTaskHandlerThread，</p>
####    endPoint.sendToUser("task is done！|用户1");此处需要用|进行分割消息内容和接收人</p>
####  10.增加TimeBeginToEndExecuteCalUtils类对时间的开始和结束进行计算，且保证当前线程下执行的时间计算</p>
####  11.增加管理员和超级管理员的注解控制，对某些方法进行注解后，只有是管理员否则超级管理员的才可以访问该方法
详情如：PermessionLimit和PermissionInterceptor及MvcInterceptorConfig类

#### 12.登录或登出，进行cookie控制，如CookieUtil及LoginController<p/>
#### 13.增加ftp逻辑，FtpUtil类和ExcelImportController类可参看调用，注意上传的文件名不支出中文<p/>
#### 14.ftp的使用场景，如：当使用数据库某个字段存储上万条对象转换为json字符串的记录时，由于一个字段存储的数据
太大，对应mysql，如果使用longtext，也会占用很大内存，因此采用将集合对象存储到txt文件里，并上传到ftp,采用追加的方式
每个对象为一行，当需要进行分页展示时，可读取行进行分页展示

如：将每一批数据的每个对象按照\n换行后拼接为字符串后上传到ftp

上传使用(在一个线程中不断从队列中取数据，然后去重，插入一天记录到template_history_data表
并上传到ftp)：

    while (true){
             try {
 
                 TempletInfoDTO templetInfoDTO = templetInfos.take();
 
                 List<NodeRelCustomDTO> nodeRelCustoms = templetInfoDTO.getNodeRelCustoms().stream().distinct().collect(Collectors.toList());
 
                 TemplateHistoryData templateHistoryData = templateHistoryDataMapper.selectByTemplateUploadId(templetInfoDTO.getUploadPathId());
                 if(templateHistoryData==null){
                     templateHistoryData = new TemplateHistoryData();
                     templateHistoryData.setTemplateUploadId(templetInfoDTO.getUploadPathId());
                     templateHistoryData.setType(templetInfoDTO.getDataType());
                     templateHistoryData.setRelNodeNum(nodeRelCustoms.size());
                     //生成文件名
                     String nameU = UUID.randomUUID().toString().replace("-", "").concat(".txt");
                     String remotefileName = templateHistoryData.getTemplateUploadId() + "_" + nameU;
                     templateHistoryData.setRemoteFileName(remotefileName);
                     templateHistoryDataMapper.insert(templateHistoryData);
                 }else{
                     templateHistoryData.setRelNodeNum(templateHistoryData.getRelNodeNum()+nodeRelCustoms.size());
                     templateHistoryDataMapper.updateByTemplateUploadId(templateHistoryData);
                 }
 
                 /**
                  * 数据遍历上传到ftp
                  */
                 String content = "";
                 for (NodeRelCustomDTO nodeRelCustom : nodeRelCustoms) {
                      content += JSONObject.toJSONString(nodeRelCustom) + "\n";
                 }
 
                 FtpUtil.appendFile(FtpAtt.getFtpAttConfig(BConstant.FTP_NODE_REL),new ByteArrayInputStream(content.getBytes()),templateHistoryData.getRemoteFileName());
 
 
                 logger.info("--------当前[{}]的---节点或关系数为 [{}] 个",templateHistoryData.getTemplateUploadId(),templateHistoryData.getRelNodeNum());
 
 
 
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
    
 分页使用：

    public NodeRelCustomPage findNodeRelCustomPageByCondition(Integer uploadFileId, int pageNo, int pageSize) throws Exception {
 
         NodeRelCustomPage nodeRelCustomPage = new NodeRelCustomPage();
 
         List<NodeRelCustomDTO> relCustomDTOS = new ArrayList<>();
         //查询模板导入节点关系历史数据
         TemplateHistoryData templateHistoryData = templateHistoryDataMapper.selectByTemplateUploadId(uploadFileId);
         if (templateHistoryData == null) {
             logger.error("模板上传id[{}]记录不存在", templateHistoryData.getTemplateUploadId());
         }
         //包含当前页的记录数和总记录数
         nodeRelCustomPage.setNodeRelCustomDTOList(relCustomDTOS);
         nodeRelCustomPage.setTotalCount(templateHistoryData.getRelNodeNum());
 
         //计算分页开始点和结束点
         int start = (pageNo - 1) * pageSize + 1;
         int end = pageNo * pageSize > templateHistoryData.getRelNodeNum() ? templateHistoryData.getRelNodeNum() : pageNo * pageSize;
 
 
         BufferedReader br = null;
         InputStream inputStream = null;
         try {
             // 获取ftp上的文件
             inputStream = FtpUtil.getFtpFileStream(FtpAtt.getFtpAttConfig(BConstant.FTP_NODE_REL), templateHistoryData.getRemoteFileName());
             //转为字节流
             br = new BufferedReader(new InputStreamReader(inputStream));
             String str = null;
             int index = 1;
             while ((str = br.readLine()) != null) {
                 if (index >= start && index <= end) {
                     NodeRelCustomDTO nodeRelCustomDTO = JSONObject.parseObject(str, NodeRelCustomDTO.class);
                     relCustomDTOS.add(nodeRelCustomDTO);
                 }
                 //如果已经大于end,直接退出
                 if (index > end) {
                     break;
                 }
                 index++;
             }
         } finally {
             if (br != null) {
                 br.close();
             }
             if (inputStream != null) {
                 inputStream.close();
             }
         }
         return nodeRelCustomPage;
     }
     
 其中 TemplateHistoryData对象专门建了一张表template_history_data永于存储上传id和存储到ftp上时的文件名及总记录数
 在resources的db目录下    
 
   

## 15.增加post获取参数放在requestBody的测试方法，主要针对，有时候参数值很大的情况
### 如：照片很大需要转换为base64后放入requestBody中
下面是获取requestBody:
    @ApiOperation(value = "post方法参数放入body的获取方式", notes = "post方法参数放入body的获取方式", response = JsonResult.class)
    @PostMapping("/requestBodyMethod")
    public void requestBodyMethod(HttpServletRequest request) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String str = "";
            String wholeStr = "";
            //一行一行的读取body体里面的内容；
            while((str = reader.readLine()) != null){
                wholeStr += str;
            }
            System.out.println("requestBody："+wholeStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
如果请求头参数值过大，则在application.properties增加属性：
####设置post请求头大小
server.max-http-header-size=10000000    
    