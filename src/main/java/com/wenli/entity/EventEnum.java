package com.wenli.entity;

public enum EventEnum {
	GroupRecall("群撤回消息"),
	NudgeEvent("戳一戳"),
	MemberJoinEvent("有新成员入群"),
	MemberLeaveEvent("有成员退群");

	private final String eventName;

	EventEnum(String eventName) {
		this.eventName = eventName;
	}

	@Override
	public String toString() {
		return eventName;
	}

	public String getEventName() {
		return eventName;
	}
}
