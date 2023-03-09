package com.wenli.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleHistoryResultVo {
	private List<SingleMessageResultVo> msgList;
	private Long total;
}
