package com.wenli.util;

import com.wenli.model.ReplayInfo;

public interface SendMessageUtil {
    void sendGroupMsg(ReplayInfo replayInfo);

    void sendFriendMsg(ReplayInfo replayInfo);
}
