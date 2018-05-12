package com.mxs.mxsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mxs.mxsserver.domain.MachineStatu;
@Repository
public interface MachineStatusRepository extends JpaRepository<MachineStatu, String> {

}
