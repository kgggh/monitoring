package com.geonhee.myprj.web.dto;


import com.geonhee.myprj.domain.host.Host;

import lombok.Getter;

@Getter
public class HostResponseDto {

    private Long id;
    private String hostName;
    private String hostAddress;
    private String lastAliveTime;

    public HostResponseDto(Host entity) {
        this.id = entity.getId();
        this.hostName = entity.getHostName();
        this.hostAddress = entity.getHostAddress();
        this.lastAliveTime = entity.getLastAliveTime();
    }
}
