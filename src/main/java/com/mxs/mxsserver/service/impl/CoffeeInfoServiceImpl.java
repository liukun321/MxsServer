package com.mxs.mxsserver.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mxs.mxsserver.domain.CoffeeInfo;
import com.mxs.mxsserver.repository.CoffeeInfoRepository;
import com.mxs.mxsserver.service.CoffeeInfoService;
@Service
public class CoffeeInfoServiceImpl implements CoffeeInfoService {
	private final Logger log = LoggerFactory.getLogger(CoffeeInfoServiceImpl.class);
	private CoffeeInfoRepository coffeeInfoRepository;
	
	
	public CoffeeInfoServiceImpl(CoffeeInfoRepository coffeeInfoRepository) {
		super();
		this.coffeeInfoRepository = coffeeInfoRepository;
	}


	@Override
	public List<CoffeeInfo> queryAllCoffeeInfo() {
		List<CoffeeInfo> list = coffeeInfoRepository.findAll();
		log.info("查询的咖啡品种：" + list.toString());
		if(null == list || list.isEmpty())
			return null;
		return list;
	}


	@Override
	public CoffeeInfo queryCoffeeInfoForPrice(Integer coffeeId) {
		return coffeeInfoRepository.findByCoffeeId(coffeeId);
	}


	@Override
	public CoffeeInfo addCoffeeInfo(CoffeeInfo coffeeInfo) {
		return coffeeInfoRepository.save(coffeeInfo);
		
	}

}
