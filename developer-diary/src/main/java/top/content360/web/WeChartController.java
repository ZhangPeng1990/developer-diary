package top.content360.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import top.content360.enums.MsgType;
import top.content360.po.TextMessage;
import top.content360.util.CheckUtil;
import top.content360.util.Log;
import top.content360.util.MessageUtil;
import top.content360.util.RequestUtil;

@Controller
@RequestMapping("/weixin")
public class WeChartController extends BaseController{

	@RequestMapping("/io")
	public String io(HttpServletRequest request, HttpServletResponse response) {

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		
		Log.log("request from : " + RequestUtil.getRemoteHost(request));
		Log.log("query uri is : " + request.getQueryString());
		
		String method = request.getMethod();
		if("GET".equals(method)){
			Log.log("GET method running...");
			authorization(request, response);
		}else if("POST".equals(method)){
			Log.log("POST method running...");
			message(request, response);
		}
		
		return null;
	}
	
	private void authorization(HttpServletRequest request, HttpServletResponse response) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.println(echostr);
		}
	}
	
	public void message(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, String> messageMap = MessageUtil.xml2Map(request);
		String toUserName = messageMap.get("ToUserName");
		String fromUserName = messageMap.get("FromUserName");
		String msgType = messageMap.get("MsgType");
		String content = messageMap.get("Content");
		
		Log.log("msgType is : " + msgType);
		Log.log("the message content is : " + content);
		
		String message = null;
		if(MsgType.text.name().equals(msgType)){
			TextMessage textMessage = new TextMessage();
			textMessage.setFromUserName(toUserName);
			textMessage.setToUserName(fromUserName);
			textMessage.setMsgType(MsgType.text.name());
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setContent("the message from you is " + content);
			
			message = MessageUtil.textMessage2Xml(textMessage);
			
			writer.println(message);
			
			writer.close();
		}else if(MsgType.event.name().equals(msgType)){
			String event = messageMap.get("Event");
			switch (event) {
			case "subscribe":
				Log.log("fromUserName 关注了本号");
				break;
			case "unsubscribe":
				Log.log("fromUserName 取消关注本号");
				break;
				
			}
		}
	}
}
