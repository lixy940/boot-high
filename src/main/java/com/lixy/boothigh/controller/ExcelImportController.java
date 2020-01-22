package com.lixy.boothigh.controller;

import com.lixy.boothigh.aop.SystemControllerLog;
import com.lixy.boothigh.constants.BConstant;
import com.lixy.boothigh.excep.ServiceException;
import com.lixy.boothigh.utils.FileRWUtils;
import com.lixy.boothigh.utils.FtpAtt;
import com.lixy.boothigh.utils.FtpUtil;
import com.lixy.boothigh.vo.page.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

/**
 * @Author: MR LIS
 * @Description:http://localhost:8080/swagger-ui.html
 * @Date: Create in 12:35 2018/4/10
 * @Modified By:
 */
@RequestMapping("/import")
@Controller
public class ExcelImportController {

    private final static Logger logger = LoggerFactory.getLogger(ExcelImportController.class);


    @GetMapping("/index")
    public String test(){
        return "import";
    }


    @PostMapping(value="/form")
    public String handleFormUpload(HttpServletRequest request,  String name, @RequestParam("file") MultipartFile file) throws Exception{
        if(!file.isEmpty()){
            //可以对user做一些操作如存入数据库
            //以下的代码是将文件file重新命名并存入Tomcat的webapps目录下项目的下级目录fileDir
            String fileRealName = file.getOriginalFilename();                   //获得原始文件名;
            int pointIndex =  fileRealName.indexOf(".");                        //点号的位置
            String fileSuffix = fileRealName.substring(pointIndex);             //截取文件后缀
            UUID FileId = UUID.randomUUID();                        //生成文件的前缀包含连字符
            String savedFileName = FileId.toString().replace("-","").concat(fileSuffix);       //文件存取名
//            String savedDir = request.getSession().getServletContext().getRealPath("fileDir"); //获取服务器指定文件存取路径
            String savedDir = BConstant.EXCEL_UPLOAD_DIR;
            File dir = new File(savedDir);
            if(!dir.exists())
                dir.mkdirs();
            File savedFile = new File(savedDir,savedFileName);
            boolean isCreateSuccess = savedFile.createNewFile();
            if(isCreateSuccess){
                file.transferTo(savedFile);  //转存文件
            }
            return "success";
        }
        return "error";
    }


    /**
     * @return
     * @Author: MR LIS
     * @Description:执行数据导入，同时存在对象需要传入时
     * @Date: 15:30 2018/4/11
     */
    @SystemControllerLog(methodDesc = "executeImportControllerAop")
    @ApiOperation(value = "添加excel数据导入记录", notes = "添加excel数据导入记录", response = JsonResult.class)
    @PostMapping(value = "executeImport")
    public JsonResult executeImport(@RequestParam("file") MultipartFile file,@RequestParam("jsonStr") String jsonStr) throws Exception {
        JsonResult jsonResult = new JsonResult();
        try {
              //对象和文件流同时上传报错，将对象转换为json字符串传递，最后转换回来
//            LinkAllVO linkAllVO = JSONObject.parseObject(jsonStr, LinkAllVO.class);

            if (file == null || file.isEmpty()) {

                jsonResult.setState(1);
                jsonResult.setMessage("文件不存在");
                return jsonResult;
            }

            //可以对user做一些操作如存入数据库
            //以下的代码是将文件file重新命名并存入Tomcat的webapps目录下项目的下级目录fileDir
            String fileRealName = file.getOriginalFilename();                   //获得原始文件名;
            int pointIndex = fileRealName.indexOf(".");                        //点号的位置
            String fileSuffix = fileRealName.substring(pointIndex);             //截取文件后缀
            UUID FileId = UUID.randomUUID();                        //生成文件的前缀包含连字符
            String savedFileName = FileId.toString().replace("-", "").concat(fileSuffix);       //文件存取名
//            String savedDir = request.getSession().getServletContext().getRealPath("fileDir"); //获取服务器指定文件存取路径
            //存储到本地
           /*  String savedDir = BConstant.EXCEL_UPLOAD_DIR; //设置路径
           File dir = new File(savedDir);
            if (!dir.exists())
                dir.mkdirs();
            File savedFile = new File(savedDir, savedFileName);
            boolean isCreateSuccess = savedFile.createNewFile();
            if (isCreateSuccess) {
                file.transferTo(savedFile);  //转存文件
            }*/

            //当前月
            String remoteFtpDir = BConstant.FTP_DIR+ FileRWUtils.getCurrentMonthPath();
            /**
             * 上传到ftp
             */
            FtpUtil.upload(FtpAtt.getFtpAttConfig(remoteFtpDir),file.getInputStream(),savedFileName);
//            FtpUtil.upload(FtpAtt.getDefaultConfig(),file.getInputStream(),savedFileName);
            //FTP上下载到本地指定目录
//            FtpUtil.downLoad(FtpAtt.getDefaultConfig(),BConstant.EXCEL_UPLOAD_DIR,BConstant.FTP_DIR,savedFileName);
            FtpUtil.downLoad(FtpAtt.getDefaultConfig(),BConstant.EXCEL_UPLOAD_DIR,remoteFtpDir,savedFileName);
        } catch (ServiceException e) {
            jsonResult.setState(1);
            jsonResult.setMessage(e.getMessageTip());
            logger.error("executeImport 异常：{}", e.getMessage(),e);
        } catch (Exception e) {
            jsonResult.setState(1);
            jsonResult.setMessage(e.getMessage());
            logger.error("executeImport 异常：{}", e.getMessage(),e);
        }

        return jsonResult;

    }

}
