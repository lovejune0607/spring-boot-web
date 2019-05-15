package io.harmontronics.seriaport.entity;

import lombok.Data;

import java.util.List;

/**
 * Create by zhou
 * Date: 2019/5/15
 * Time: 16:54
 */
@Data
public class ServerStatus {

    /**
     * 端口号
     */
    private String portName;

    /**
     * 能否打开
     */
    private boolean canOpen;

    /**
     * 失败原因
     */
    private String failReason;

    /**
     * 手表列表
     */
    private List<WatchInfo> watchList;
}
