package io.harmontronics.seriaport.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Create by zhou
 * Date: 2019/5/14
 * Time: 10:50
 */
@Entity
@Data
@Table(name = "od_watch_info")
public class WatchInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     *  手表地址
     */
    private String watchAddress;

    /**
     *  发送主机地址
     */
    private String address;

    public WatchInfo(){

    }

    public WatchInfo(String watchAddress,String address){
        this.watchAddress = watchAddress;
        this.address = address;
    }
}
