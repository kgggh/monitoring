package com.geonhee.myprj.web.dto;


import java.time.LocalDateTime;

import com.geonhee.myprj.domain.host.Host;

import lombok.Getter;

@Getter
public class HostResponseDto {

    private Long id;
    private String hostName;
    private String hostAddress;
    private String alive;
    private LocalDateTime lastAliveTime;

    public HostResponseDto(Host entity) {
        this.id = entity.getId();
        this.hostName = entity.getHostName();
        this.hostAddress = entity.getHostAddress();
        this.alive = entity.getAlive();
        this.lastAliveTime = entity.getLastAliveTime();
    }
}
