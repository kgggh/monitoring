package com.geonhee.myprj.service.host;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geonhee.myprj.domain.host.Host;
import com.geonhee.myprj.domain.host.HostRepository;
import com.geonhee.myprj.web.dto.HostSaveRequestDto;
import com.geonhee.myprj.web.dto.HostUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HostService {
	
	private final HostRepository hostRepository;

	@Transactional
	public Long save(HostSaveRequestDto requestDto) {
        return hostRepository.save(requestDto.toEntity()).getId();
    }
	
	@Transactional
	public void update(Long id,HostUpdateRequestDto requestDto) {
		Host host = hostRepository.findById(id).
				orElseThrow(()-> new IllegalArgumentException()); 
		host.update(requestDto.getAlive(),requestDto.getLastAliveTime());
	}
    
	@Transactional(readOnly = true)
    public Optional<Host> findByhostName(String hostName) {
    	return hostRepository.findByHostName(hostName);
    }
    
	@Transactional(readOnly = true)
	public long hostCount() {
		return hostRepository.count();
	}
	
	@Transactional(readOnly = true)
    public List<Host> findAll(){
    	return hostRepository.findAll();
    }
	
	@Transactional(readOnly = true)
    public Optional<Host> findByhostNameOrHostAddress(HostSaveRequestDto hostSaveRequestDto){
    	return hostRepository.findByHostNameOrHostAddress(hostSaveRequestDto.getHostName(),
    			hostSaveRequestDto.getHostAddress());
    }
    
}
