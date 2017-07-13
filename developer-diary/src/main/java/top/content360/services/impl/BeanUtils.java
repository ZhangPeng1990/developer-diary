package top.content360.services.impl;

import java.util.Date;

import top.content360.entities.MessagePojo;
import top.content360.po.Message;

public class BeanUtils {

	public static Message toMessage(MessagePojo pojo){
		Message m = new Message();
		
		m.setToUserName(pojo.getToUserName());
		m.setFromUserName(pojo.getFromUserName());
		m.setCreateTime(pojo.getCreateTime().getTime());
		m.setMsgType(pojo.getMsgType());
		
		return m;
	}
	
	public static MessagePojo toMessagePojo(Message message){
		MessagePojo pojo = new MessagePojo();
		
		pojo.setToUserName(message.getToUserName());
		pojo.setFromUserName(message.getFromUserName());
		pojo.setCreateTime(new Date());
		pojo.setMsgType(message.getMsgType());
		pojo.setEvent(message.getEvent());
		return pojo;
	}
}
