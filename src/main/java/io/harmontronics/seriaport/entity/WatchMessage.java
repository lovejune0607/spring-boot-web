package io.harmontronics.seriaport.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "od_watch_message")
@EntityListeners(AuditingEntityListener.class)
public class WatchMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 发送内容
     */
    private String content;
    /**
     * 发送主机地址
     */
    private String address;

    /**
     * 发送时间
     */
    @CreatedDate
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 发送状态
     * 0-成功
     * 1-失败
     */
    private String status;
    /**
     * 发送失败原因
     */
    @Column(name = "fail_reason")
    private String failReason;

    @LastModifiedDate
    @Column(name = "send_time")
    private Date sendTime;

    public WatchMessage(){}


    public WatchMessage(String content,String address){
        this.content = content;
        this.address = address;
    }

}
