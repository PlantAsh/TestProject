package test;

import java.util.Scanner;

public class MailMain {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		System.out.println("start send mail...\n------不输入内容可以直接输入OK------");
		
		MailBean bean = new MailBean();
		
		Scanner scanner = new Scanner(System.in);
		scanner.useDelimiter("\\s*ok\\s*");
		
		System.out.println("输入标题，输入OK完成：");
		bean.setSubject(scanner.next());
		
		System.out.println("输入内容，输入OK完成：");
		bean.setText(scanner.next());
		
		System.out.println("输入时间(例：2017-10-14 10:28)，输入OK完成(当前时间输入OK跳过)：");
		bean.setDate(scanner.next());
		
		System.out.println("输入邮件重要性数字(1:紧急   3:普通    5:低)，输入OK完成(输入OK可跳过)：");
		bean.setPriority(scanner.next());
		
		System.out.println("输入附件完整路径，多个路径以','分隔，输入OK完成(输入OK可跳过)：");
		String path = scanner.next();
		if (path.length() < 1) {
			bean.setFilepath(null);
		} else {
			bean.setFilepath(path.split(",|，"));
		}
		
		String receive = "";
		while (receive.length() < 1) {
			System.out.println("输入对方邮箱，输入OK完成：");
			receive = scanner.next();
		}
		bean.setReceive(receive);
		
		String pwd = "";
		while (pwd.length() < 1) {
			System.out.println("输入你的邮箱密码，输入OK完成：");
			pwd = scanner.next();
		}
		bean.setPwd(pwd);
		
		SendMail mail = new SendMail(bean);
		mail.send();
		
		System.out.println("end...");
	}

}
