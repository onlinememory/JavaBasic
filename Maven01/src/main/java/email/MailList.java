package email;


import org.apache.commons.mail.util.MimeMessageParser;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class MailList {
    public static void main(String[] args) {
        String emlFileFolderStr = "C:\\MyVirtualPC\\VM-Share\\email\\sent";
        String emlFile = "C:\\MyVirtualPC\\VM-Share\\email\\12.2021 Gehaltsabrechnung-Houjun Liu.eml";

        File emlFolder = new File(emlFileFolderStr);
        File[] emlList = emlFolder.listFiles();
        for (File eml : emlList) {
            readEml(eml.getAbsolutePath());
        }
    }

    public static void readEml(String emlFile) {
        Properties props = System.getProperties();
        props.put("mail.host", "smtp.dummydomain.com");
        props.put("mail.transport.protocol", "smtp");

        Session mailSession = Session.getDefaultInstance(props, null);
        MimeMessage message;
        try {
            InputStream source = new FileInputStream(emlFile);
            message = new MimeMessage(mailSession, source);
            String sender = message.getFrom()[0].toString();
            sender = message.getAllRecipients()[0].toString();
            if (!sender.contains(".hk") && !sender.contains("zhangnan") && !sender.contains("finance")
                    && !sender.contains("lyan@travelsky.com")
                    && !sender.contains("admin@travelsky.com.cn")
                    && !sender.contains("csc@travelsky.com.cn")
                    && !sender.contains("jwzhao@travelsky.com.cn")
                    && !sender.contains("leimingtong@travelsky.com.cn")
                    && !sender.contains("kate.huang")) {
                int start = sender.indexOf("<");
                int end = sender.indexOf(">");

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = format.format(message.getSentDate());

                if (start != -1) {
                    sender = sender.substring(start + 1, sender.indexOf(">"));
                    String subject = message.getSubject();
                    subject = subject.replace("回复: ", "");
                    subject = subject.replace("回覆: ", "");
                    subject = subject.replace("轉寄: ", "");
                    subject = subject.replace("转发: ", "");
                    subject = subject.replace("RE: ", "");
                    subject = subject.replace("Re: ", "");
                    subject = subject.replace("答复: ", "");
                    subject = subject.replace("Automatic reply: ", "");
                    System.out.println(sender + " " + subject + "\t" + date);
                }
//                subject = subject.replace("#","");
            }
        } catch (MessagingException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
