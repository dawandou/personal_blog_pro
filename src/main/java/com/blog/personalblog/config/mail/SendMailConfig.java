package com.blog.personalblog.config.mail;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

/**
 * 邮件发送
 * @author: SuperMan
 * @create: 2022-01-24
 **/
@Log4j2
public class SendMailConfig {

    @Value("${send.mail.host}")
    private static String host;

    @Value("${send.mail.port}")
    private static Integer port;

    @Value("${send.mail.from}")
    private static String from;

    @Value("${send.mail.pass}")
    private static String pass;

    /**
     * 邮件发送实现方法
     * @param mailInfo
     */
    public static void sendMail(MailInfo mailInfo) {
        try {
            MailAccount account = new MailAccount();
            //邮件服务器的SMTP地址
            account.setHost(host);
            //邮件服务器的SMTP端口
            account.setPort(port);
            //发件人
            account.setFrom(from);
            //密码
            account.setPass(pass);
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
