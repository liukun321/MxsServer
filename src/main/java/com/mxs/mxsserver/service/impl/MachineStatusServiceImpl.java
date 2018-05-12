package com.mxs.mxsserver.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mxs.mxsserver.domain.MachineStatu;
import com.mxs.mxsserver.repository.MachineStatusRepository;
import com.mxs.mxsserver.service.MachineStatusService;
@Service
public class MachineStatusServiceImpl implements MachineStatusService {
	private final Logger log = LoggerFactory.getLogger(MachineStatusServiceImpl.class);
	private MachineStatusRepository machineStatusRepository;
	
	
	public MachineStatusServiceImpl(MachineStatusRepository machineStatusRepository) {
		super();
		this.machineStatusRepository = machineStatusRepository;
	}

	@Transactional
	@Override
	public void addMachineStatus(MachineStatu machineStatus) {
		try {
			machineStatusRepository.save(machineStatus);
		}catch(Exception e) {
			log.info(e.getMessage() , e);
		}
	}

}
