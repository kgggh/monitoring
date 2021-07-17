package com.geonhee.myprj.service.host;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.geonhee.myprj.domain.host.Host;
import com.geonhee.myprj.domain.host.HostRepository;
import com.geonhee.myprj.web.dto.HostResponseDto;
import com.geonhee.myprj.web.dto.HostSaveRequestDto;
import com.geonhee.myprj.web.dto.HostUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HostService {
	
	private final HostRepository hostRepository;

	@Transactional()
	public Long save(HostSaveRequestDto requestDto) {
        return hostRepository.save(requestDto.toEntity()).getId();
    }
	
	@Transactional()
	public void update(Long id,HostUpdateRequestDto requestDto) {
		Host host = hostRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("")); 
			host.update(requestDto.getAlive(),requestDto.getLastAliveTime());
			hostRepository.saveAndFlush(host);
	}
    
    public HostResponseDto findByhostName(String hostName) {
    	Host entity = hostRepository.findByHostName(hostName);
    return new HostResponseDto(entity);
    }
    
    public List<Host> findAll(){
    	return hostRepository.findAll();
    }
    
}
