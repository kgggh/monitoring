package com.geonhee.myprj.service.monitoring;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.geonhee.myprj.domain.host.Host;
import com.geonhee.myprj.domain.host.HostRepository;
import com.geonhee.myprj.web.dto.HostResponseDto;
import com.geonhee.myprj.web.dto.HostSaveRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HostService {
	
	private final HostRepository hostRepository;

	@Transactional()
	public Long save(HostSaveRequestDto requestDto) {
        return hostRepository.save(requestDto.toEntity()).getId();
    }

    public HostResponseDto findById(Long id) {
    	Host entity = hostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id가 존재하지 않는다.=" + id));
    return new HostResponseDto(entity);
    }
    
    public List<Host> findAll(){
    	return hostRepository.findAll();
    }
}
