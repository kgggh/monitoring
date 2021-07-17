package com.geonhee.myprj.service.monitoring;

import java.net.InetAddress;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.geonhee.myprj.service.host.HostService;
import com.geonhee.myprj.web.dto.HostResponseDto;
import com.geonhee.myprj.web.dto.HostUpdateRequestDto;

@Service
public class MonitoringService {
	@Autowired
	private HostService hostSerivce;
	
    @Scheduled(fixedDelay = 1000)
    @Async(value = "fooExecutor")
    public void test() throws Exception{
    	hostSerivce.findAll().parallelStream().forEach(host->{
    		realTimeMonitoring(hostSerivce.findByhostName(host.getHostName()));
    	});
    	System.out.println("완료");
    }
    
    public void realTimeMonitoring(HostResponseDto hostResponDto) {
    	HostUpdateRequestDto hostUpdateRequestDto = new HostUpdateRequestDto();
    	try {
    	    InetAddress iaddr = InetAddress.getByName(hostResponDto.getHostName());
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
    	    hostSerivce.update(hostResponDto.getId(), hostUpdateRequestDto);
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println("---------에러--------");
    	}
    }

}
