package toolUtils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class SendMailUtils {
	
	
	
//	public static void main(String[] args) {
//		
//		sendMail("yun@yyh.com","fuck you");
//	}

	/**
	 * 外网邮件发送
	 * @param to
	 * @param code
	 */
	public static void sendMail(String to,String code){
		// Session对象:
		Properties props = new Properties();
		//邮件服务器在本机
		//props.setProperty("mail.host", "192.168.107.23");
		
		//邮件服务器在外网需要用下面两个配置替代
	    props.setProperty("mail.host", "192.168.109.85");
//		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				//邮件服务器在本机
				return new PasswordAuthentication("192.168.109.85", "root");
				
				
				//邮件服务器在外网
//				return new PasswordAuthentication("******@163.com", "1qaz2wsx");
			}
			
		});
		// Message对象:
		Message message = new MimeMessage(session);
		// 设置发件人：
		try {
			message.setFrom(new InternetAddress("store.@store.com"));
			// 设置收件人:
			message.addRecipient(RecipientType.TO, new InternetAddress(to));
			// 设置主题:
			message.setSubject("激活码");
			// 设置内容：
			//String url = "http://localhost:8080/store_v1.0/UserServlet?method=active&code="+code;
			message.setContent(code,"text/html;charset=UTF-8");
			// Transport对象:
			Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}

}
