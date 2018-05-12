package com.mxs.mxsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mxs.mxsserver.domain.CoffeeInfo;
@Repository
public interface CoffeeInfoRepository extends JpaRepository<CoffeeInfo, Integer> {

	CoffeeInfo findByCoffeeId(Integer coffeeId);

}
