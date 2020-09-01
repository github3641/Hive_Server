package org.example.dc.srv.timing.job;

import org.apache.commons.mail.EmailException;
import org.example.dc.srv.utils.SendEmailUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * Project: Hive_Server
 * Package: org.example.dc.srv.timing.job
 * Class: SendMailJob
 * Author: RuiChao Lv
 * Date: 2020/9/1
 * Version: 1.0
 * Description:
 */

@Component
@EnableScheduling
public class SendMailJob {
    @Autowired
    private SendEmailUtil sendEmailUtil;
    public void sendEmail() throws JobExecutionException {
        String address="779235932@qq.com,lv_ruichao@163.com";
        String subject="测试邮件";
        String msg="<html>\n<body>你能收到吗？\n</body>\n</html>";

        String status=null;
        try {
            status = sendEmailUtil.sendMail(address, subject, msg);
        } catch (EmailException e) {
            e.printStackTrace();
        }
        System.out.println("发送邮件:"+status);
    }
}
