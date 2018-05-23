package com.mxs.mxsserver.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
	 * 查询错误记录
	 */
	@Override
	public ErrorRecord queryErrorRecord(String machineId) {
		return errorRecordRepository.findByMachineIdAndEndTimeIsNull(machineId);
	}

	@Override
	public List<ErrorRecord> queryErrorRecordByCoffeeId(String machineId) {
		List<ErrorRecord> list = errorRecordRepository.findByMachineId(machineId);
		if(null == list || list.isEmpty())
			return null;
		return list;
	}

}
