package com.blog.personalblog.config.mail;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j2;

/**
 * 邮件发送
 * @author: SuperMan
 * @create: 2022-01-24
 **/
@Log4j2
public class SendMailConfig {

    /**
     * 邮件发送实现方法
     * @param mailInfo
     */
    public static void sendMail(MailInfo mailInfo) {
        try {
            MailAccount account = new MailAccount();
            //邮件服务器的SMTP地址
            account.setHost("smtp.163.com");
            //邮件服务器的SMTP端口
            account.setPort(25);
            //发件人
            account.setFrom("xyh2390472338@163.com");
            //密码
            account.setPass("SRIHHGBIOEGOCAIC");
            //使用SSL安全连接
            account.setSslEnable(false);
            MailUtil.send(account, mailInfo.getReceiveMail(),
                    mailInfo.getTitle(), mailInfo.getContent(), false);
            log.info("邮件发送成功！");
        } catch (Exception e) {
            log.error("邮件发送失败" + JSONUtil.toJsonStr(mailInfo));
        }

    }

}
