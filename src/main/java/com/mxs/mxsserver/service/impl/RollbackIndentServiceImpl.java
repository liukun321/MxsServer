package com.mxs.mxsserver.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mxs.mxsserver.domain.RollbackIndent;
import com.mxs.mxsserver.repository.RollbackIndentRepository;
import com.mxs.mxsserver.service.RollbackIndentService;
@Transactional
@Service
public class RollbackIndentServiceImpl implements RollbackIndentService {
	private final Logger log = LoggerFactory.getLogger(RollbackIndentServiceImpl.class);
	
	private RollbackIndentRepository rollbackIndentRepository;
	
	public RollbackIndentServiceImpl(RollbackIndentRepository rollbackIndentRepository) {
		super();
		this.rollbackIndentRepository = rollbackIndentRepository;
	}

	@Override
	public RollbackIndent addRollbackIndent(RollbackIndent rollbackIndent) {
		RollbackIndent rollback = null;
		try {
			rollback = rollbackIndentRepository.save(rollbackIndent);
		}catch(Exception e) {
			log.info(e.getMessage() , e);
		}
		return rollback;
	}

}
