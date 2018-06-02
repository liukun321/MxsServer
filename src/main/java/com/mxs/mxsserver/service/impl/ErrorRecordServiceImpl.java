package com.mxs.mxsserver.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mxs.mxsserver.domain.ErrorRecord;
import com.mxs.mxsserver.repository.ErrorRecordRepository;
import com.mxs.mxsserver.service.ErrorRecordService;
/**
 * 咖啡机错误记录service层
 * @author liukun
 *	
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ErrorRecordServiceImpl implements ErrorRecordService{
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	private ErrorRecordRepository errorRecordRepository;
	
	public ErrorRecordServiceImpl(ErrorRecordRepository errorRecordRepository) {
		super();
		this.errorRecordRepository = errorRecordRepository;
	}

	/**
	 * 添加错误记录
	 */
	@Override
	public ErrorRecord addErrorRecord(ErrorRecord errorRecord) {
//		
		return errorRecordRepository.save(errorRecord);
	}

	/**
	 * 查询错误记录， 定位到没有结束的错误记录
	 */
	@Override
	public List<ErrorRecord> queryErrorRecord(String machineId) {
		return errorRecordRepository.findByMachineIdAndEndTimeIsNull(machineId);
	}

	@Override
	public List<ErrorRecord> queryErrorRecordByCoffeeId(String machineId) {
		List<ErrorRecord> list = errorRecordRepository.findByMachineId(machineId);
		if(null == list || list.isEmpty())
			return null;
		return list;
	}
	/**
	 * 同时更新的数量小，所以不做分批处理
	 */
	@Transactional
	@Override
	public void batchUpdate(List<ErrorRecord> ers) {
		ers.stream().forEach(error -> entityManager.merge(error));
		entityManager.flush();
		entityManager.clear();
	}

}
