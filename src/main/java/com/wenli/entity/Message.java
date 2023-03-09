package com.wenli.entity;

import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.message.data.ImageType;

import java.util.ArrayList;
import java.util.List;

public class Message {
	private Long loginQq;
	//原文字消息
	private String text;
	//文字消息的第一节
	private String keyword;
	//文字消息的参数
	private List<String> args = new ArrayList<>();
	//发送人qq
	private Long qq;
	//发送人昵称
	private String name;
	//群号
	private Long groupId;
	//图片url集合
	private List<String> imgUrlList = new ArrayList<>();
	//图片类型集合
	private List<ImageType> imgTypeList = new ArrayList<>();
	//是否被呼叫
	private Boolean isCallMe = false;
	//艾特了哪些人
	private List<Long> atQQList = new ArrayList<>();
	//发送时间戳
	private Integer time;
	//消息缩略字符串
	private String eventString;
	//接收到的事件
	private EventEnum event;
	//是否要发送消息
	private Boolean isReplay = true;
	//用户权限
	private MemberPermission userAdmin;
}
