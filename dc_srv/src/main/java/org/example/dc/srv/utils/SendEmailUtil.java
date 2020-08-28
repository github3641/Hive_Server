package org.example.dc.srv.utils;

/**
 * Project: Hive_Server
 * Package: org.example.dc.srv.utils
 * Class: SendEmailUtil
 * Author: RuiChao Lv
 * Date: 2020/8/21
 * Version: 1.0
 * Description:
 */
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.example.dc.srv.enums.ExecutionStatusEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SendEmailUtil {

    public static String sendMail(String address, String subject, String msg) throws EmailException {
        //读取配置文件信息
        String filePath="dc_srv/src/main/resources/app.yml";
        Map<String, String> mailParameter = YamlUtils.getYamlByFileName(filePath);
        String hostName = mailParameter.get("sendMail.hostName");
        String SSLOnConnect = mailParameter.get("sendMail.SSLOnConnect");
        String smtpPort = mailParameter.get("sendMail.smtpPort");
        String charset = mailParameter.get("sendMail.charset");
        String addressFrom = mailParameter.get("sendMail.addressFrom");
        String nameFrom = mailParameter.get("sendMail.nameFrom");
        String userName = mailParameter.get("sendMail.userName");
        String passWord = mailParameter.get("sendMail.passWord");


        String status=ExecutionStatusEnum.FAILED.getMsg();
        //参数检查
        if (StringUtils.isEmpty(address) || StringUtils.isEmpty(subject) || StringUtils.isEmpty(msg)) {
            throw new EmailException("传入参数有空项!");
        }

        try {
            HtmlEmail email = new HtmlEmail();
            List<String> list = new ArrayList();
            String[] addressArr = address.split(",");

            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"
            email.setHostName(hostName);
            email.setSSLOnConnect(Boolean.parseBoolean(SSLOnConnect));
            email.setSmtpPort(Integer.parseInt(smtpPort));
            // 字符编码集的设置
            email.setCharset(charset);
            // 收件人的邮箱
            email.addTo(addressArr);
            // 发送人的邮箱以及发件人名称
            email.setFrom(addressFrom, nameFrom);
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
            email.setAuthentication(userName, passWord);//这里的密码是授权码
            // 要发送的邮件主题
            email.setSubject(subject);
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
            email.setMsg(msg);
            //发送
            email.send();
            status = ExecutionStatusEnum.SUCCESS.getMsg();
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EmailException("发送邮件失败!");
        }
    }
}