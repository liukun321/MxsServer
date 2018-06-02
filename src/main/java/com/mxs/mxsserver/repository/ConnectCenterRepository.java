package com.mxs.mxsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mxs.mxsserver.domain.CoffeeInfo;
import com.mxs.mxsserver.domain.ConnectCenter;
@Repository
public interface ConnectCenterRepository extends JpaRepository<ConnectCenter, Integer> {

}
