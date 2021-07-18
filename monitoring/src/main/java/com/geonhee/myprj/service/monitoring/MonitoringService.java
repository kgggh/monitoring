package com.geonhee.myprj.service.monitoring;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.geonhee.myprj.domain.host.Host;
import com.geonhee.myprj.service.host.HostService;
import com.geonhee.myprj.web.dto.HostUpdateRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MonitoringService {
	final HostService hostSerivce;
	
    @Scheduled(fixedRate = 1000)
    @Async(value = "fooExecutor")
    public void test() throws Exception{
    	log.info("시작");
    	hostSerivce.findAll().parallelStream().forEach(host->{
    		realTimeMonitoring(hostSerivce.findByhostName(host.getHostName()));
    	});
    	log.info("완료");
    }
    
    public void realTimeMonitoring(Optional<Host> hostResponseDto) {
    	HostUpdateRequestDto hostUpdateRequestDto = new HostUpdateRequestDto();
    	try {
    	    InetAddress iaddr = InetAddress.getByName(hostResponseDto.get().getHostName());
    	    System.out.println(iaddr.getHostAddress());
    	    boolean reachable = iaddr.isReachable(1000);
    	    if(reachable) {
    	    	hostUpdateRequestDto = HostUpdateRequestDto.builder()
    	    			.alive("Y")
    	    			.lastAliveTime(LocalDateTime.now())
    	    			.build();
    	    }else {
    	    	hostUpdateRequestDto = HostUpdateRequestDto.builder()
    	    			.alive("N")
    	    			.build();
    	    }
    	    hostSerivce.update(hostResponseDto.get().getId(), hostUpdateRequestDto);
    	} catch (Exception e) {
    		System.out.println("----에러---- \n 해당 호스트이름: " + hostResponseDto.get().getHostName());
    	}
    }

}
