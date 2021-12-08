package com.lxj.shardingjdbc.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.*;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Xingjing.Li
 * @since 2021/11/30
 */
@Aspect
@Component
public class LogRecordAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogRecordAspect.class);
    //解析spel表达式
    ExpressionParser parser = new SpelExpressionParser();
    //将方法参数纳入Spring管理
    LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
    // 参数名发现器
    private DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Pointcut("@annotation(com.lxj.shardingjdbc.log.LogRecord)")
    public void operationPointcut(){}

    @Around("operationPointcut()")
    public Object operationLog(ProceedingJoinPoint pjp) throws Throwable {
        LOGGER.info("开始记录操作日志");
        //获取参数对象数组
        Object[] args = pjp.getArgs();
        //方法执行结果
        MethodExecuteResult methodExecuteResult = new MethodExecuteResult(true, null, "");
        //获取方法
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        LogRecord logRecord = method.getAnnotation(LogRecord.class);
        //获取方法参数名
        String[] params = discoverer.getParameterNames(method);
        //将参数纳入Spring管理
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        Object ret = null;
        try {
            ret = pjp.proceed();
        } catch (Exception e) {
            methodExecuteResult = new MethodExecuteResult(false, e, e.getMessage());
        }
        Map<String, Object> variables = LogRecordContext.getVariables();
        if (variables != null && variables.size() > 0) {
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                context.setVariable(entry.getKey(), entry.getValue());
            }
        }
        try {
            recordExecution(ret, method, args, logRecord,
                    methodExecuteResult.isSuccess(), methodExecuteResult.getErrorMessage(), context);
        } catch (Exception t) {
            //记录日志错误不要影响业务
            LOGGER.error("log record parse exception", t);
        } finally {
            LogRecordContext.clear();
        }
        return ret;
    }

    private void recordExecution(Object ret, Method method, Object[] args, LogRecord logRecord,
                                 boolean success, String errorMessage, EvaluationContext context) {
        String logInfo = "";
        if (success) {
            Expression expression = parser.parseExpression(logRecord.success());
            logInfo = expression.getValue(context, String.class);
            LOGGER.info(logInfo);
        }else {
            if (StringUtils.isEmpty(logRecord.fail())) {
                LOGGER.info("{} execute failed, errorMessage:{}", method, errorMessage);
            }else {
                Expression expression = parser.parseExpression(logRecord.fail());
                logInfo = expression.getValue(context, String.class);
                LOGGER.info(logInfo);
            }
        }
    }

    //    @After(value = "operationPointcut()")
    public void beforeLog(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LogRecord logRecord = signature.getMethod().getAnnotation(LogRecord.class);

        // 构建日志存储对象
//        AuditingLog auditlog = AuditingLog.builder().applicationName(applicationName).createTime(LocalDateTime.now()).build();
//
//        auditlog.setUserId(xxx);  // 从上下文获取当前操作的用户信息
//        auditlog.setUserNickname(xx);

        // 设置操作的接口方法名
//        auditlog.setInterfaceName(signature.getDeclaringTypeName()+"."+signature.getName());

        // 获得日志注解上自定义的日志信息

        // Spel表达式解析日志信息
        // 获得方法参数名数组
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(signature.getMethod());
        if (parameterNames != null && parameterNames.length > 0) {
            EvaluationContext context = new StandardEvaluationContext();
            //获取方法参数值
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                // 替换spel里的变量值为实际值， 比如 #user -->  user对象
                context.setVariable(parameterNames[i], args[i]);
            }
            // 解析出实际的日志信息
//            String operationInfo = parser.parseExpression(logInfo).getValue(context).toString();
            // 打印日志信息
//            LOGGER.info(operationInfo);
        }
        //TODO 这时可以将日志信息auditlog进行异步存储,比如写入到文件通过logstash增量的同步到Elasticsearch或者DB

    }

}
