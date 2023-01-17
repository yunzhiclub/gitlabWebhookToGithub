package club.yunzhi.webhook.service;

import club.yunzhi.webhook.entities.GitlabRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

/**
 * 合并事件
 */
public interface CombineEventService {

    /**
     * @param queueIterator 待处理的所有请求的迭代器
     * @param gitlabRequest 当前处理的请求
     * @return json
     */
    String commentAndIssueClose(Iterator<GitlabRequest> queueIterator, GitlabRequest gitlabRequest) throws IOException;

    /**
     * 对gitlabRequest进行总事件处理
     * @param queueIterator 待处理的数据
     * @return json
     */
    String handleEvent(Iterator<GitlabRequest> queueIterator, GitlabRequest gitlabRequest) throws IOException;
}
