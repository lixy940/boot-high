package com.lixy.boothigh.controller;

import com.lixy.boothigh.aop.SystemControllerLog;
import com.lixy.boothigh.enums.ResultEnum;
import com.lixy.boothigh.excep.ServiceException;
import com.lixy.boothigh.service.GenCommonService;
import com.lixy.boothigh.vo.page.ColumnInfoVO;
import com.lixy.boothigh.vo.page.JsonResult;
import com.lixy.boothigh.vo.page.SandPageViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: MR LIS
 * @Description:
 * @Date: Create in 16:40 2018/5/25
 * @Modified By:
 */
@Api(tags = {"数据库公共接口"})
@RestController
@RequestMapping("/gencommon")
public class GenCommonController {

    private final static Logger logger = LoggerFactory.getLogger(GenCommonController.class);

    @Autowired
    private GenCommonService genCommonService;

    /**
     * @return
     * @Author: MR LIS
     * @Description: 根据数据库id、表名获取表各列的列名、注释及数据类型
     * @Date: 16:46 2018/5/25
     */
    @SystemControllerLog(methodDesc = "getAllColumnInfoControllerAop")
    @ApiOperation(value = "获取表所有列信息", notes = "根据数据库id、表名获取表各列的列名、注释及数据类型", consumes = "application/json", response = JsonResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "dbId", dataType = "Integer", required = true, value = "数据库配置id", defaultValue = ""),
            @ApiImplicitParam(paramType = "path", name = "tableName", dataType = "String", required = true, value = "数据库表名", defaultValue = "")
    })
    @GetMapping("getAllColumnInfo/{dbId}/{tableName}")
    public JsonResult getAllColumnInfo(@PathVariable("dbId") Integer dbId, @PathVariable("tableName") String tableName) {
        JsonResult jsonResult = new JsonResult();
        try {
            List<ColumnInfoVO> allColumnInfo = genCommonService.getAllColumnInfo(dbId, tableName);
            jsonResult.setData(allColumnInfo);

        } catch (ServiceException e) {
            logger.error("获取表的列信息异常:{}", e.getMessage());
            jsonResult.setState(ResultEnum.SERVER_ERROR.getValue());
            jsonResult.setMessage("获取表的列信息异常：" + e.getMessage());
        } catch (Exception e) {
            logger.error("获取表的列信息异常:{}", e.getMessage());
            jsonResult.setState(ResultEnum.SERVER_ERROR.getValue());
            jsonResult.setMessage("服务器错误");
        }
        return jsonResult;
    }

    /**
     * @return
     * @Author: MR LIS
     * @Description: 获取分页列表信息
     * @Date: 16:46 2018/5/25
     */
    @SystemControllerLog(methodDesc = "executePageControllerAop")
    @ApiOperation(value = "获取分页列表", notes = "获取分页列表信息", consumes = "application/json", response = JsonResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "dbId", dataType = "Integer", required = true, value = "数据库id", defaultValue = ""),
            @ApiImplicitParam(paramType = "path", name = "tableName", dataType = "String", required = true, value = "数据库表名", defaultValue = ""),
            @ApiImplicitParam(paramType = "path", name = "pageNum", dataType = "Integer", required = true, value = "当前第几页", defaultValue = ""),
            @ApiImplicitParam(paramType = "path", name = "pageSize", dataType = "Integer", required = true, value = "每页记录数", defaultValue = "")
    })
    @GetMapping("executePage/{dbId}/{tableName}/{pageNum}/{pageSize}")
    public JsonResult executePage(@PathVariable("dbId") Integer dbId, @PathVariable("tableName") String tableName, @PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        JsonResult jsonResult = new JsonResult();
        try {
            SandPageViewVO sandPageViewVO = genCommonService.executePageQuery(dbId, tableName, pageNum, pageSize);
            jsonResult.setData(sandPageViewVO);

        } catch (ServiceException e) {
            logger.error("获取分页列表信息异常:{}", e.getMessage());
            jsonResult.setState(ResultEnum.SERVER_ERROR.getValue());
            jsonResult.setMessage("获取分页列表信息异常：" + e.getMessage());
        } catch (Exception e) {
            logger.error("获取分页列表信息异常:{}", e.getMessage());
            jsonResult.setState(ResultEnum.SERVER_ERROR.getValue());
            jsonResult.setMessage("服务器错误");
        }
        return jsonResult;
    }

    /**
     * @return
     * @Author: MR LIS
     * @Description: 获取分页列表信息,不进行总记录数查询
     * @Date: 16:46 2018/5/25
     */
    @SystemControllerLog(methodDesc = "executePageNotCountControllerAop")
    @ApiOperation(value = "获取不含总记录数的分页列表", notes = "获取分页列表信息,不返回总记录数", consumes = "application/json", response = JsonResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "dbId", dataType = "Integer", required = true, value = "数据库id", defaultValue = ""),
            @ApiImplicitParam(paramType = "path", name = "tableName", dataType = "String", required = true, value = "数据库表名", defaultValue = ""),
            @ApiImplicitParam(paramType = "path", name = "pageNum", dataType = "Integer", required = true, value = "当前第几页", defaultValue = ""),
            @ApiImplicitParam(paramType = "path", name = "pageSize", dataType = "Integer", required = true, value = "每页记录数", defaultValue = "")
    })
    @GetMapping("executePageNotCount/{dbId}/{tableName}/{pageNum}/{pageSize}")
    public JsonResult executePageNotCount(@PathVariable("dbId") Integer dbId, @PathVariable("tableName") String tableName, @PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        JsonResult jsonResult = new JsonResult();
        try {

            List<List<Object>> dataList = genCommonService.executePageQueryNotCount(dbId, tableName, pageNum, pageSize);
            jsonResult.setData(dataList);

        } catch (ServiceException e) {
            logger.error("获取分页列表信息异常:{}", e.getMessage());
            jsonResult.setState(ResultEnum.SERVER_ERROR.getValue());
            jsonResult.setMessage("获取分页列表信息异常：" + e.getMessage());
        } catch (Exception e) {
            logger.error("获取分页列表信息异常:{}", e.getMessage());
            jsonResult.setState(ResultEnum.SERVER_ERROR.getValue());
            jsonResult.setMessage("服务器错误");
        }
        return jsonResult;
    }

    

}
