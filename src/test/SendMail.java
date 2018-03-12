package test;

import javax.mail.Message.RecipientType;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Multipart;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {

	private static String SMTPSERVER = "smtp.ym.163.com";
	private static String SMTPPORT = "465"; 
	private static String account = "ws@mingcloud.cn";
	private MailBean bean = new MailBean();
	
	public SendMail() {
		// TODO Auto-generated constructor stub
	}
	
	SendMail(MailBean bean) {
		// TODO Auto-generated constructor stub
		this.bean = bean;
	}
	
	public void send() throws Exception {
		try {
			
			// 创建邮件配置
			Properties properties = new Properties();
			// 使用的协议（JavaMail规范要求）
			properties.setProperty("mail.transport.protocol", "smtp");
			// 发件人的邮箱的 SMTP 服务器地址
			properties.setProperty("mail.smtp.host", SMTPSERVER);
			properties.setProperty("mail.smtp.port", SMTPPORT); 
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			// 需要请求认证
			properties.setProperty("mail.smtp.auth", "true");
			// 开启ssl
			properties.setProperty("mail.smtp.ssl.enable", "true");
			
			// 根据邮件配置创建会话
			Session session = Session.getDefaultInstance(properties);
			session.setDebug(true);
			//创建邮件
	        MimeMessage message = creatMail(session);
	        //获取传输通道
	        Transport transport = session.getTransport();
	        //连接，并发送邮件
	        transport.connect(SMTPSERVER, account, bean.getPwd());
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private MimeMessage creatMail(Session session) throws Exception {
		// 根据会话创建邮件
		MimeMessage message = new MimeMessage(session);
		// 设置发送邮件方
		// address邮件地址, personal邮件昵称, charset编码方式
		InternetAddress fromAddress = new InternetAddress(account, "wrkjs", "utf-8");
		message.setFrom(fromAddress);
		// 设置接收邮件方
		InternetAddress receiveAddress = new InternetAddress(bean.getReceive(), bean.getReceive(), "uf-8");
		message.setRecipient(RecipientType.TO, receiveAddress);
		
		// 设置邮件标题
		message.setSubject(bean.getSubject(), "utf-8");
		// 添加附件内容
		Multipart multipart = new MimeMultipart();
		if (bean.getFilepath() != null && !bean.getFilepath().equals("")) {
			for (int i = 0; i < bean.getFilepath().length; i++) {
				String filename = bean.getFilepath()[i];
				MimeBodyPart bodyPart = new MimeBodyPart();
				// 得到数据源
				FileDataSource source = new FileDataSource(filename);
				// 得到附件本身并至入BodyPart 
				bodyPart.setDataHandler(new DataHandler(source));
				// 得到文件名同样至入BodyPart  
				bodyPart.setFileName(source.getName());
				multipart.addBodyPart(bodyPart);
			}
			MimeBodyPart bodyPart = new MimeBodyPart();
			bodyPart.setText(bean.getText());
			multipart.addBodyPart(bodyPart);
			message.setContent(multipart);
		} else {
			// 设置邮件内容
			message.setText(bean.getText());
//			message.setContent("<body><p>测试格式</p><h1><p>格式</p><h1></body>", "text/html;charset=utf-8");
		}
		
		// 设置显示的发件时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		if (bean.getDate() != null && !bean.getDate().equals("")) {
			date = sdf.parse(bean.getDate());
		}
		message.setSentDate(date);
		// 保存设置
		message.saveChanges();
		
		return message;
	}
	
}
