package com.lixy.boothigh.controller;

import com.lixy.boothigh.aop.SystemControllerLog;
import com.lixy.boothigh.bean.DataBaseConfig;
import com.lixy.boothigh.dao.DataBaseConfigMapper;
import com.lixy.boothigh.enums.DBTypeEnum;
import com.lixy.boothigh.enums.ResultEnum;
import com.lixy.boothigh.excep.ServiceException;
import com.lixy.boothigh.service.GenCommonService;
import com.lixy.boothigh.utils.RedisLock;
import com.lixy.boothigh.vo.ConditionCountVo;
import com.lixy.boothigh.vo.ConditionPageVo;
import com.lixy.boothigh.vo.page.ColumnInfoVO;
import com.lixy.boothigh.vo.page.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: MR LIS
 * @Description:根据数据库配置id,表名等获取对应信息，此处需要加入redis分布式锁，防止过多人同时访问，jdbc连接过多，造成系统崩溃
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
    @Autowired
    private DataBaseConfigMapper dataBaseConfigMapper;

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
        RedisLock lock = new RedisLock("SandCommonController_getAllColumnInfo");
        try {
           /* //如果指定时间没有拿到锁就直接返回为空，拿到锁进行查询
            if (!lock.getLock()) {
                jsonResult.setData(new ArrayList<>());
                jsonResult.setState(ResultEnum.SERVER_ERROR.getValue());
                jsonResult.setMessage("当前查询用户过多，请稍后重试");
            }else {*/
                List<ColumnInfoVO> allColumnInfo = genCommonService.getAllColumnInfo(dbId, tableName);
                jsonResult.setData(allColumnInfo);
//            }
        } catch (ServiceException e) {
            logger.error("获取表的列信息异常:{}", e.getMessage());
            jsonResult.setState(ResultEnum.SERVER_ERROR.getValue());
            jsonResult.setMessage("获取表的列信息异常：" + e.getMessage());
        } catch (Exception e) {
            logger.error("获取表的列信息异常:{}", e.getMessage());
            jsonResult.setState(ResultEnum.SERVER_ERROR.getValue());
            jsonResult.setMessage("服务器错误");
        }/*finally {
            lock.unlock();
        }*/
        return jsonResult;
    }

    /**
     * @return
     * @Author: MR LIS
     * @Description: 获取分页列表总记录数
     * @Date: 16:46 2018/5/25
     */
    @SystemControllerLog(methodDesc = "executePageTotalCountControllerAop")
    @ApiOperation(value = "获取分页列表总记录数", notes = "获取分页列表总记录数", consumes = "application/json", response = JsonResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "dbId", dataType = "Integer", required = true, value = "数据库id", defaultValue = ""),
            @ApiImplicitParam(paramType = "path", name = "tableName", dataType = "String", required = true, value = "数据库表名", defaultValue = "")
    })
    @GetMapping("executePage/{dbId}/{tableName}")
    public JsonResult executePageTotalCount(@PathVariable("dbId") Integer dbId, @PathVariable("tableName") String tableName) {
        JsonResult jsonResult = new JsonResult();

        try {

            int total = genCommonService.executePageTotalCount(dbId, tableName);
            jsonResult.setData(total);

        } catch (ServiceException e) {
            logger.error("获取分页列表总记录数:{}", e.getMessage());
            jsonResult.setState(ResultEnum.SERVER_ERROR.getValue());
            jsonResult.setMessage("获取分页列表总记录数：" + e.getMessage());
        } catch (Exception e) {
            logger.error("获取分页列表总记录数:{}", e.getMessage());
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
            List<ColumnInfoVO> allColumnInfo = genCommonService.getAllColumnInfo(dbId, tableName);
            DataBaseConfig dbInfo = dataBaseConfigMapper.selectOne(dbId);
            String columnArr = null;
            //oracle,pg库不支持"`"
            if(DBTypeEnum.DB_MYSQL.getDbName().equalsIgnoreCase(dbInfo.getDbType()) || DBTypeEnum.DB_TIDB.getDbName().equals(dbInfo.getDbType())){
                List<String> collect = allColumnInfo.stream().map(o -> "`" + o.getColumnEname() + "`").collect(Collectors.toList());
                columnArr = StringUtils.join(collect, ",");
            }else{
                List<String> collect = allColumnInfo.stream().map(o ->  o.getColumnEname()).collect(Collectors.toList());
                columnArr = StringUtils.join(collect, ",");
            }
            List<List<Object>> dataList = genCommonService.executePageQueryColumnRecord(dbId, tableName,columnArr, pageNum, pageSize);
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


    /**
     * @return
     * @Author: MR LIS
     * @Description: 获取分页列表信息
     * @Date: 16:46 2018/5/25
     */
    @ApiOperation(value = "获取带条件分页列表总记录数", notes = "获取带条件分页列表总记录数", response = JsonResult.class)
    @PostMapping("/executePageTotalCountWithCondition")
    public JsonResult executePageTotalCountWithCondition(@RequestBody ConditionCountVo countVo) {
        JsonResult responseResult = new JsonResult();
        try {
            int totalCount = genCommonService.executePageTotalCountWithCondition(countVo);
            responseResult.setData(totalCount);

        } catch (ServiceException e) {
            logger.error("获取带条件分页列表总记录数异常:{}", e.getMessage(), e);
            responseResult.setState(ResultEnum.SERVER_ERROR.getValue());
            responseResult.setMessage("获取带条件分页列表总记录数异常：" + e.getMessage());
        } catch (Exception e) {
        }
        return responseResult;
    }

    /**
     * @return
     * @Author: MR LIS
     * @Description: 获取分页列表信息, 不进行总记录数查询
     * @Date: 16:46 2018/5/25
     */
    @ApiOperation(value = "获取不含总记录数的带条件分页列表", notes = "获取带条件分页列表信息,不返回总记录数", response = JsonResult.class)
    @PostMapping("/executePageNotCountWithCondition")
    public JsonResult executePageNotCountWithCondition(@RequestBody ConditionPageVo pageVo) {
        JsonResult responseResult = new JsonResult();
        try {
            List<ColumnInfoVO> allColumnInfo = genCommonService.getAllColumnInfo(pageVo.getDbId(), pageVo.getTableName());
            DataBaseConfig dbInfo = dataBaseConfigMapper.selectOne(pageVo.getDbId());
            String columnArr = null;
            //oracle,pg库不支持"`"
            if(DBTypeEnum.DB_MYSQL.getDbName().equalsIgnoreCase(dbInfo.getDbType()) || DBTypeEnum.DB_TIDB.getDbName().equals(dbInfo.getDbType())){
                List<String> collect = allColumnInfo.stream().map(o -> "`" + o.getColumnEname() + "`").collect(Collectors.toList());
                columnArr = StringUtils.join(collect, ",");
            }else{
                List<String> collect = allColumnInfo.stream().map(o ->  o.getColumnEname()).collect(Collectors.toList());
                columnArr = StringUtils.join(collect, ",");
            }
            List<List<Object>> dataList = genCommonService.executePageQueryNotCountWithCondition(pageVo,columnArr);
            responseResult.setData(dataList);

        } catch (ServiceException e) {
            logger.error("获取带分页分页列表信息异常:{}", e.getMessage(),e);
            responseResult.setState(ResultEnum.SERVER_ERROR.getValue());
            responseResult.setMessage("获取带分页分页列表信息异常：" + e.getMessage());
        } catch (Exception e) {
            logger.error("获取带分页分页列表信息异常:{}", e.getMessage(),e);
            responseResult.setState(ResultEnum.SERVER_ERROR.getValue());
            responseResult.setMessage("服务器错误");
        }
        return responseResult;
    }
}
