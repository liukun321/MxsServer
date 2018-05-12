package com.mxs.mxsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mxs.mxsserver.domain.ErrorRecord;
@Repository
public interface ErrorRecordRepository extends JpaRepository<ErrorRecord, Integer> {

}
