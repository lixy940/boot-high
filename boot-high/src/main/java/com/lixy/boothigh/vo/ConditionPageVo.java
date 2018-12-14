package com.lixy.boothigh.vo;

import java.util.List;

/**
 * @author LIS
 * @date 2018/12/1416:46
 */
public class ConditionPageVo{
        /**
         *数据库id
         */
        private Integer dbId;
        /**
         * 表名
         */
        private String tableName;
        /**
         * 第几页
         */
        private Integer pageNum;
        /**
         * 每页记录数
         */
        private Integer pageSize;
        /**
         * 条件集合对象
         */
        private List<ConditionVo> conditionVos;

        public Integer getDbId() {
                return dbId;
        }

        public void setDbId(Integer dbId) {
                this.dbId = dbId;
        }

        public String getTableName() {
                return tableName;
        }

        public void setTableName(String tableName) {
                this.tableName = tableName;
        }

        public Integer getPageNum() {
                return pageNum;
        }

        public void setPageNum(Integer pageNum) {
                this.pageNum = pageNum;
        }

        public Integer getPageSize() {
                return pageSize;
        }

        public void setPageSize(Integer pageSize) {
                this.pageSize = pageSize;
        }

        public List<ConditionVo> getConditionVos() {
                return conditionVos;
        }

        public void setConditionVos(List<ConditionVo> conditionVos) {
                this.conditionVos = conditionVos;
        }
}
