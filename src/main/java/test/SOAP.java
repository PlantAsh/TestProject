package test;

import java.net.URL;
import java.util.Scanner;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import org.w3c.dom.Document;

public class SOAP {

	public static void main(String[] args) throws Exception {
		String ns = "urn:apipro:apiinterface2";
        String wsdlUrl = "http://116.62.209.251:8081/OnlineKPI/Eam/APIINTERFACE2.asmx?WSDL";
//        String wsdlUrl = "http://10.87.59.88/OnlineKPI/Eam/APIINTERFACE2.asmx?wsdl";
        //1、创建服务(Service)
        URL url = new URL(wsdlUrl);
        QName sname = new QName(ns,"APIINTERFACE2");
        Service service = Service.create(url,sname);
                    
        //2、创建Dispatch
        Dispatch<SOAPMessage> dispatch = service.createDispatch(new QName(ns,"APIINTERFACE2Soap"),SOAPMessage.class,Service.Mode.MESSAGE);
                    
        //3、创建SOAPMessage
        SOAPMessage msg = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL).createMessage();
        SOAPEnvelope envelope = msg.getSOAPPart().getEnvelope();
        SOAPBody body = envelope.getBody();
                    
        //4、创建QName来指定消息中传递数据
        QName ename = new QName(ns,"WebLogin","axis");//<nn:add xmlns="xx"/>
        SOAPBodyElement ele = body.addBodyElement(ename);
        // 传递参数 
        System.out.println("输入账号：");
        Scanner scanner = new Scanner(System.in);
        String userKey = scanner.next();
        System.out.println("输入密码：");
        Scanner scanner2 = new Scanner(System.in);
        String password = scanner2.next();
        
        ele.addChildElement("userName").setValue(userKey);  
        ele.addChildElement("password").setValue(password);  
        msg.writeTo(System.out);
        System.out.println("\n invoking.....");
                            
        //5、通过Dispatch传递消息,会返回响应消息
        SOAPMessage response = dispatch.invoke(msg);
        response.writeTo(System.out);
        System.out.println();
                    
        //6、响应消息处理,将响应的消息转换为dom对象
        Document doc = response.getSOAPPart().getEnvelope().getBody().extractContentAsDocument();
        String str = doc.getElementsByTagName("WebLoginResponse").item(0).getTextContent();
        System.out.println(str);
	}
}
