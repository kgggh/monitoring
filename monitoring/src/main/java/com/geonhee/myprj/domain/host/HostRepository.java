package com.geonhee.myprj.domain.host;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<Host, Long>{
	Optional<Host> findByHostName(String hostName);
	Optional<Host> findByHostNameOrHostAddress(String hostName, String hostAddress);
}
