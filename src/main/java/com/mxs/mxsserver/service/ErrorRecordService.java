package com.mxs.mxsserver.service;

import java.util.List;

import com.mxs.mxsserver.domain.ErrorRecord;

public interface ErrorRecordService {
	ErrorRecord addErrorRecord(ErrorRecord errorRecord);
	
	List<ErrorRecord> queryErrorRecord(String machineId);
	
	List<ErrorRecord> queryErrorRecordByCoffeeId(String machineId);

	void batchUpdate(List<ErrorRecord> ers);
}
