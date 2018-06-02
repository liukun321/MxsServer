package com.mxs.mxsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mxs.mxsserver.domain.MobilePay;

@Repository
public interface MobilePayRepository extends JpaRepository<MobilePay, String> {

}
