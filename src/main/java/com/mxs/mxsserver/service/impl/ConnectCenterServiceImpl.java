package com.mxs.mxsserver.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mxs.mxsserver.domain.ConnectCenter;
import com.mxs.mxsserver.repository.ConnectCenterRepository;
import com.mxs.mxsserver.service.ConnectCenterService;
/**
 * 管理中心联系方式列表
 * @author liukun
 *
 */
@Service
public class ConnectCenterServiceImpl implements ConnectCenterService {
	
	private ConnectCenterRepository connectCenterRepository;
	
	public ConnectCenterServiceImpl(ConnectCenterRepository connectCenterRepository) {
		super();
		this.connectCenterRepository = connectCenterRepository;
	}

	@Override
	public List<ConnectCenter> queryAllConnection() {
		return connectCenterRepository.findAll();
	}

}
