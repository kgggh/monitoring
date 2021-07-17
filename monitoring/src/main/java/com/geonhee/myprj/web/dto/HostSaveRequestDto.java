package com.geonhee.myprj.web.dto;


import java.time.LocalDateTime;

import com.geonhee.myprj.domain.host.Host;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HostSaveRequestDto {
	@ApiModelProperty(value = "호스트이름")
    private String hostName;
	
	@ApiModelProperty(value = "호스트주소")
	private String hostAddress;
	
	@ApiModelProperty(value = "활성화상태")
	private String alive;
	
	@ApiModelProperty(value = "최종활성화시간")
	private LocalDateTime lastAliveTime;
	
    @Builder
    public HostSaveRequestDto(String hostName, String hostAddress, String alive, LocalDateTime lastAliveTime) {
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
