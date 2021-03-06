package com.mxs.mxsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mxs.mxsserver.domain.DiscountInfo;
@Repository
public interface DiscountInfoRepository extends JpaRepository<DiscountInfo, String> {

	DiscountInfo findByVenderName(String venderName);

}
