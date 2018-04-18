package com.lixy.boothigh.controller;

import com.lixy.boothigh.constants.BConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

/**
 * @Author: MR LIS
 * @Description:
 * @Date: Create in 12:35 2018/4/10
 * @Modified By:
 */
@RequestMapping("/import")
@Controller
public class ExcelImportController {


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

}
