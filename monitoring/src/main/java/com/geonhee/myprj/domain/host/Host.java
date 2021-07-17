package com.geonhee.myprj.domain.host;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Entity
@ToString
@DynamicUpdate
public class Host {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(unique = true)
    private String hostName;

    @Column(unique = true)
    private String hostAddress;
    
    @ColumnDefault("N")
    private String alive;

    @Column(name="lastAliveTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastAliveTime;

    @Builder
    public Host(String hostName, String hostAddress,String alive, LocalDateTime lastAliveTime){
        this.hostName = hostName;
        this.hostAddress = hostAddress;
        this.alive = alive;
        this.lastAliveTime = lastAliveTime;
    }
    
    public void update(String alive, LocalDateTime lastAliveTime){
    	this.alive = alive;
    	this.lastAliveTime = lastAliveTime;
    }
}	

