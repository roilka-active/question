/**
 *
 */
package com.roilka.roilka.question.domain.component.intercept;


import com.roilka.roilka.question.common.utils.StringUtils;
import com.roilka.roilka.question.dal.entity.user.User;
import com.roilka.roilka.question.domain.component.bean.BaseBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.util.Date;
import java.util.Properties;

/**
 * mybatis 拦截器
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
@Slf4j
public class AuditInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.debug("sql 添加用户信息");
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        if (parameter instanceof BaseBean) {
            populateAuditBean((BaseBean) parameter, mappedStatement.getSqlCommandType());
        }
        return invocation.proceed();
    }

    private void populateAuditBean(BaseBean baseBean, SqlCommandType sqlCommandType) {
        //获取上下文
        User user = new User();
        if (sqlCommandType == SqlCommandType.INSERT) {
            if (null == baseBean.getCreateTime()) {
                baseBean.setCreateTime(new Date());
            }
            if (user != null) {
                if (StringUtils.isBlank(baseBean.getCreateUserCode())) {
                    baseBean.setCreateUserCode(user.getUserCode());
                }
                if (StringUtils.isBlank(baseBean.getCreateUserName())) {
                    baseBean.setCreateUserName(user.getUserName());
                }
            } else if (StringUtils.isNotBlank(baseBean.getCreateUserCode()) && StringUtils.isNotBlank(baseBean.getCreateUserName())) {
            } else {
                baseBean.setCreateUserCode("system");
                baseBean.setCreateUserName("system");
            }
        } else if (sqlCommandType == SqlCommandType.UPDATE) {
            baseBean.setUpdateTime(new Date());
            if (user != null) {
                baseBean.setUpdateUserCode(user.getUserCode());
                baseBean.setUpdateUserName(user.getUserName());
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}
