package com.geonhee.myprj.web.dto;


import com.geonhee.myprj.domain.host.Host;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HostSaveRequestDto {
    private String hostName;
    private String hostAddress;
    private String lastAliveTime;
    @Builder
    public HostSaveRequestDto(String hostName, String hostAddress, String lastAliveTime) {
        this.hostName = hostName;
        this.hostAddress = hostAddress;
        this.lastAliveTime = lastAliveTime;
    }

    public Host toEntity() {
        return Host.builder()
                .hostName(hostName)
                .hostAddress(hostAddress)
                .lastAliveTime(lastAliveTime)
                .build();
    }
}
