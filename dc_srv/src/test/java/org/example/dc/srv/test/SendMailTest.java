package org.example.dc.srv.test;

import org.apache.commons.mail.EmailException;
import org.example.dc.srv.utils.SendEmailUtil;

/**
 * Project: Hive_Server
 * Package: org.example.dc.srv.test
 * Class: SendMailTest
 * Author: RuiChao Lv
 * Date: 2020/8/21
 * Version: 1.0
 * Description:测试邮件发送工具
 */
public class SendMailTest {
    public static void main(String args[]){
        String address="**********";
        String subject="测试邮件";
        String msg="<html>\n<body>你能收到吗？\n</body>\n</html>";
        SendEmailUtil sendEmailUtil = new SendEmailUtil();
        String status=null;
        try {
            status = sendEmailUtil.sendMail(address, subject, msg);
        } catch (EmailException e) {
            e.printStackTrace();
        }
        System.out.println("发送邮件:"+status);

    }
}
