/**
 *
 */
package indi.zyf.sso.core.security.verification.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.zyf.sso.core.ErrorCode;
import indi.zyf.sso.core.SystemConstants;
import indi.zyf.sso.core.security.exception.VerificationCodeException;
import indi.zyf.sso.core.security.verification.generator.VerificationCodeGenerator;
import indi.zyf.sso.core.security.verification.pojo.VerificationCode;
import indi.zyf.sso.core.security.verification.pojo.VerificationCodeType;
import indi.zyf.sso.mapper.VerificationCodeRecordMapper;
import indi.zyf.sso.model.VerificationCodeRecord;
import indi.zyf.sso.util.JsonUtil;
import indi.zyf.sso.util.NetworkUtil;
import indi.zyf.sso.util.RedisUtil;
import indi.zyf.sso.util.StringUtil;
import indi.zyf.sso.vo.ApiResult;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;


/**
 * @Function: AbstractVerificationCodeProcessor
 * @Description: 验证码处理器抽象类
 *
 * 				子类命名规则 xxxVerificationCodeProcessor 例：ImageVerificationCodeProcessor
 *
 * @param:
 * @return:
 * @throws: 异常描述
 *
 * @version: v1.0.0
 * @author: zyf
 * @date: 2019/1/16 11:35
 *
 * Modification History:
 * Date                  Author        Version         Description
 *-----------------------------------------------------------------*
 * 2019/1/16 11:35      zyf            v1.0.0           修改原因
 */
@Component
public abstract class AbstractVerificationCodeProcessor<C extends VerificationCode> implements VerificationCodeProcessor {

    protected final Log logger = LogFactory.getLog(getClass());

    @Value("${debug}")
    private String debug;
    /**
     * redis工具类
     */
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 收集系统中所有的 {@link VerificationCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, VerificationCodeGenerator> validateCodeGenerators;

    @Autowired
    private VerificationCodeRecordMapper verificationCodeRecordMapper;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        try {
            C validateCode = generate(request);//生成验证码
            save(request, validateCode);//保存
            send(request, validateCode);//发送
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            throw e;
        }
    }

    /**
     * 生成校验码
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        //获取类型
        String type = getVerificationCodeType(request).toString().toLowerCase();
        String generatorName = type + VerificationCodeGenerator.class.getSimpleName();
        VerificationCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new VerificationCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 保存校验码
     *
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, C validateCode) {
        try {
            String key = getRedisKey(request);
            logger.info("rediskey:" + key);
            //存入redis，保存10分钟
            redisUtil.set(key, validateCode, 600);
        } catch (ServletRequestBindingException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    /**
     * 构建验证码存储redis时的key
     *
     * @param request
     * @return
     */
    private String getRedisKey(ServletWebRequest request) throws ServletRequestBindingException {
        //用户名，手机号等
        String username = ServletRequestUtils.getStringParameter(request.getRequest(), "username");
        if (StringUtils.isEmpty(username)) {
            throw new VerificationCodeException(ErrorCode.ERR_SYS_WRONG_PARAMETER.getErrMsg());
        }
        return username + "_" + SESSION_KEY_PREFIX + getVerificationCodeType(request).toString().toUpperCase();
    }

    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param verificationCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C verificationCode) throws Exception;

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private VerificationCodeType getVerificationCodeType(ServletWebRequest request) {
        //查询对应的验证码类型
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "VerificationCodeProcessor");
        return VerificationCodeType.valueOf(type.toUpperCase());
    }

    /**
     * @Function: AbstractVerificationCodeProcessor
     * @Description: 校验验证码
     *
     * @param:
     * @return:
     * @throws: 异常描述
     *
     * @version: v1.0.0
     * @author: zyf
     * @date: 2019/1/18 19:58
     *
     * Modification History:
     * Date                  Author        Version         Description
     *-----------------------------------------------------------------*
     * 2019/1/16 11:35      zyf            v1.0.0           修改原因
     */
    @Override
    public void validate(ServletWebRequest request) {
        //获取验证码类型
        VerificationCodeType processorType = getVerificationCodeType(request);
        String key = null;
        try {
            //获取存储验证码的key
            key = getRedisKey(request);
        } catch (ServletRequestBindingException e) {
            logger.error(e);
            e.printStackTrace();
        }
        //从redis中拿回验证码
        C codeInRedis = (C) redisUtil.get(key);

        String codeInRequest;
        try {
            //获取前端传来的验证码
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new VerificationCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new VerificationCodeException(processorType + "验证码的值不能为空");
        }

        if (codeInRedis == null) {
            throw new VerificationCodeException(processorType + "验证码不存在");
        }
        if (new Date().after(codeInRedis.getExpireTime())) {
            redisUtil.del(key);
            throw new VerificationCodeException(processorType + "验证码已过期");
        }
        if ("true".equals(debug)) {
            if(StringUtils.equalsIgnoreCase(SystemConstants.DEFAULT_DEBUG_CODE,codeInRequest)){
                redisUtil.del(key);
                return;
            }
        }
        if (!checkVerificationCode(codeInRedis.getCode(), codeInRequest)) {
            throw new VerificationCodeException(processorType + "验证码不匹配");
        }

        redisUtil.del(key);
    }

    /**
    * @Function: AbstractVerificationCodeProcessor
    * @Description: 查看验证码是否匹配，子类可以有其他实现
    *
    * @param:
    * @return:
    * @throws: 异常描述
    *
    * @version: v1.0.0
    * @author: zyf
    * @date: 2019/1/29 10:42
    *
    * Modification History:
    * Date                  Author        Version         Description
    *-----------------------------------------------------------------*
    * 2019/1/29 10:42      zyf            v1.0.0           修改原因
    */
    public boolean checkVerificationCode(String codeInRequest,String codeInRedis){
        return StringUtils.equalsIgnoreCase(codeInRedis, codeInRequest);
    };

    /**
     * @Function: AbstractVerificationCodeProcessor
     * @Description: 响应数据到前端
     *
     * @param:
     * @return:
     * @throws: 异常描述
     *
     * @version: v1.0.0
     * @author: zyf
     * @date: 2019/1/18 22:11
     *
     * Modification History:
     * Date                  Author        Version         Description
     *-----------------------------------------------------------------*
     * 2019/1/18 22:11      zyf            v1.0.0           修改原因
     */
    public void respond(ServletResponse response, ApiResult apiResult) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(apiResult));
    }


    /**
     * @Function: AbstractVerificationCodeProcessor
     * @Description: 验证码发送记录保存
     *
     * @param:
     * @return:
     * @throws: 异常描述
     *
     * @version: v1.0.0
     * @author: zyf
     * @date: 2019/1/23 14:27
     *
     * Modification History:
     * Date                  Author        Version         Description
     *-----------------------------------------------------------------*
     * 2019/1/23 14:27      zyf            v1.0.0           修改原因
     */
    @Transactional
    public void saveSendRecord(ServletWebRequest request, VerificationCode verificationCode, VerificationCodeType type) {
        try {
            VerificationCodeRecord record = new VerificationCodeRecord();
            record.setUsername(ServletRequestUtils.getStringParameter(request.getRequest(), "username"));
            record.setCodeContent(JsonUtil.toJson(verificationCode));
            record.setType(type.getParamNameOnValidate());
            record.setCreateTime(new Date());
            record.setIp(NetworkUtil.getIpAddress(request.getRequest()));
            record.setState(1);
            verificationCodeRecordMapper.insertSelective(record);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
}
