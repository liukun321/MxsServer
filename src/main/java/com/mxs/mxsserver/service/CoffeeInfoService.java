package com.mxs.mxsserver.service;

import java.util.List;

import com.mxs.mxsserver.domain.CoffeeInfo;

public interface CoffeeInfoService {
	//查询咖啡信息
	List<CoffeeInfo> queryAllCoffeeInfo();
	//查询咖啡的价格
	Double queryCoffeeInfoForPrice(Integer coffeeId);
	//添加咖啡的品种
	CoffeeInfo addCoffeeInfo(CoffeeInfo coffeeInfo);
	
}
