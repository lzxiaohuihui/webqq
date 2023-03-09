package com.wenli.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
	private String gid; //对应 groupId
	private String title = "";// 群名称
	private String desc = ""; //群描述
	private String img = "/img/zwsj5.png";//群图片
	private String code; //群号，唯一标识
	private Integer userNum = 1; // 群成员数量，避免某些情况需要多次联表查找，如搜索；所以每次加入一人，数量加一
	private Date createDate = new Date();
	private String holderName; // 群主账号，在user实体中对应name字段
	private String holderUserId; //群人员的id，作为关联查询
}
