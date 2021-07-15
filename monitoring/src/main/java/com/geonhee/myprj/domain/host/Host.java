package com.geonhee.myprj.domain.host;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.geonhee.myprj.domain.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Host extends BaseTimeEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    
    @Column(name="port")
    private int port;

    @Column(unique = true)
    private String hostName;

    @Column(unique = true)
    private String hostAddress;

    @Column(name="lastAliveTime")
    private String lastAliveTime;

    @Builder
    public Host(int port,String hostName, String hostAddress, String lastAliveTime){
    	this.port = port;
        this.hostName = hostName;
        this.hostAddress = hostAddress;
        this.lastAliveTime = lastAliveTime;
    }
}	

