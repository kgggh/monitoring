package com.geonhee.myprj.web.dto;


import java.time.LocalDateTime;

import com.geonhee.myprj.domain.host.Host;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HostUpdateRequestDto {
    private String hostName;
    private String hostAddress;
    private String alive;
    private LocalDateTime lastAliveTime;
    @Builder
    public HostUpdateRequestDto(String hostName, String hostAddress,String alive, LocalDateTime lastAliveTime) {
        this.hostName = hostName;
        this.hostAddress = hostAddress;
        this.alive = alive;
        this.lastAliveTime = lastAliveTime;
    }

    public Host toEntity() {
        return Host.builder()
                .hostName(hostName)
                .hostAddress(hostAddress)
                .alive(alive)
                .lastAliveTime(lastAliveTime)
                .build();
    }
}
