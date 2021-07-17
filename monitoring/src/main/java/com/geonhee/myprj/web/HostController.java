package com.geonhee.myprj.web;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.geonhee.myprj.domain.host.Host;
import com.geonhee.myprj.service.errors.Message;
import com.geonhee.myprj.service.errors.StatusEnum;
import com.geonhee.myprj.service.host.HostService;
import com.geonhee.myprj.web.dto.HostResponseDto;
import com.geonhee.myprj.web.dto.HostSaveRequestDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class HostController {
	private final HostService hostService;

	@ApiOperation(value = "단일 호스트 조회",notes = "호스트이름으로 호스트를 조회한다")
    @GetMapping(value = "/api/host/{hostName}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> findByHost (@PathVariable String hostName) {
    	HostResponseDto hostResponseDto = hostService.findByhostName(hostName);
    	Message message = new Message();
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공");
        message.setData(hostResponseDto);
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }
    
	@ApiOperation(value = "호스트 리스트 조회",notes = "모든 호스트를 조회한다.")
    @GetMapping(value = "/api/host", produces = MediaType.APPLICATION_JSON_VALUE) 
    public ResponseEntity<Message> findAllHost () {
    	List<Host> hostResponseDto = hostService.findAll();
    	Message message = new Message();
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공");
        message.setData(hostResponseDto);
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }
    
	@ApiOperation(value = "호스트 등록",notes = "호스트를 등록한다")
    @PostMapping(value = "/api/host",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long save(@RequestBody HostSaveRequestDto hostSaveRequestDto ) {
        return hostService.save(hostSaveRequestDto);
    }
}
