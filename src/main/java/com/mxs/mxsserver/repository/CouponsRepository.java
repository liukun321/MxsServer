package com.mxs.mxsserver.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mxs.mxsserver.domain.Coupons;
@Repository
public interface CouponsRepository extends JpaRepository<Coupons, Integer> {
//	@Query("select * from Coupons c where c.couponCode=?1 and c.endTime > ?2")
	Coupons findByCouponCodeAndEndTimeAfter(String couponCode, Date date);

}
