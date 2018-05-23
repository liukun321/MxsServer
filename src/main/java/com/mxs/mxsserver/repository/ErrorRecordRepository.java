package com.mxs.mxsserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mxs.mxsserver.domain.ErrorRecord;
@Repository
public interface ErrorRecordRepository extends JpaRepository<ErrorRecord, Integer> {

	ErrorRecord findByMachineIdAndEndTimeIsNull(String machineId);

	List<ErrorRecord> findByMachineId(String machineId);

}
