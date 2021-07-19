package com.geonhee.myprj.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.geonhee.myprj.domain.host.Host;
import com.geonhee.myprj.service.host.HostService;
import com.geonhee.myprj.web.dto.HostSaveRequestDto;
import com.geonhee.myprj.web.response.BasicResponse;
import com.geonhee.myprj.web.response.CommonResponse;
import com.geonhee.myprj.web.response.ErrorResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api("Host Contorller API")
@RequiredArgsConstructor
@RestController
public class HostController {
	private final HostService hostService;
	
	@GetMapping("greeting")
	public String greeting() {
		return "test";
	}

	@ApiOperation(value = "단일 호스트 조회",notes = "호스트이름으로 호스트를 조회한다")
	@ApiImplicitParam(name = "hostName", value = "호스트이름",paramType = "path")
	@ApiResponses({
		@ApiResponse(code = 200,message = "성공"),
		@ApiResponse(code = 404,message = "동륵되지 않은 호스트")
	})
	@GetMapping(value = "/api/host/{hostName}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> findByHost (@PathVariable String hostName) {
		Optional<Host> oHost = hostService.findByhostName(hostName);
		if(!oHost.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body(new ErrorResponse("동륵되지 않은 호스트"));
		}
        return ResponseEntity.ok().
        		body(new CommonResponse<Host>(oHost.get()));
    }
    
	
	@ApiOperation(value = "호스트 리스트 조회",notes = "모든 호스트를 조회한다.")
	@ApiResponse(code = 200,message = "성공")
    @GetMapping(value = "/api/host", produces = MediaType.APPLICATION_JSON_VALUE) 
    public ResponseEntity<? extends BasicResponse> findAllHost () {
    	List<Host> hostResponseDto = hostService.findAll();
        return ResponseEntity.ok().body(new CommonResponse<List<Host>>(hostResponseDto));
    }
    
	
	@ApiOperation(value = "호스트 등록",notes = "호스트를 등록한다")
	@ApiResponses({
		@ApiResponse(code = 204,message = "성공"),
		@ApiResponse(code = 409,message = "등록된 호스트가 존재"),
		@ApiResponse(code = 423,message = "최대 100개까지 등록가능"),
	})
	@PostMapping(value = "/api/host", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> save(@RequestBody HostSaveRequestDto hostSaveRequestDto ) {
		if(hostService.findByhostNameOrHostAddress(hostSaveRequestDto).isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).
					body(new ErrorResponse("등록된 호스트가 존재합니다."));
		}
		if(hostService.hostCount()==100) {
			return ResponseEntity.status(HttpStatus.LOCKED).
					body(new ErrorResponse("최대 100개까지 등록가능합니다."));
		}
		hostService.save(hostSaveRequestDto);
        return ResponseEntity.noContent().build();
    }
}
