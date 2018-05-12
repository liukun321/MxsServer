package com.mxs.mxsserver.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mxs.mxsserver.domain.Payindent;
import com.mxs.mxsserver.repository.PayindentRepository;
import com.mxs.mxsserver.service.PayindentService;
@Transactional
@Service
public class PayindentServiceImpl implements PayindentService {
	private final Logger log = LoggerFactory.getLogger(PayindentServiceImpl.class);
	
	private PayindentRepository payindentRepository;
	
	
	public PayindentServiceImpl(PayindentRepository payindentRepository) {
		super();
		this.payindentRepository = payindentRepository;
	}

	/**
	 * 添加订单
	 */
	@Override
	public Payindent addPayindent(Payindent payindent) {
		Payindent p = null;
		try {
			p = payindentRepository.save(payindent);
		}catch(Exception e) {
			log.info(e.getMessage() , e);
		}
		return p;
	}
	/**
	 * 删除订单-》撤销订单
	 */
	@Override
	public boolean deletePayindent(String indentId) {
		boolean flag = false;
		try {
			Payindent payindent = payindentRepository.findOne(indentId);
			if(null == payindent) {
				log.info("The payindent of " + indentId + " does not exist");
				flag = false;
			}else {
				//删除订单-》将订单修改为 支付失败
				payindent.setPayStatus(5);
				payindentRepository.save(payindent);
				flag = true;
			}
		}catch(Exception e) {
			log.info(e.getMessage() , e);
			return flag;
		}
		return flag;
		
	}
	/**
	 * 根据订单ID查询订单
	 */
	@Override
	public Payindent queryPayindentById(String indentId) {
		return payindentRepository.findByIndentId(indentId);
	}

	/**
	 * 根据订单的状态查询订单
	 */
	@Override
	public List<Payindent> queryOrderByStatus(Integer status) {
		return payindentRepository.findByPayStatus(status);
	}

	/**
	 * 根据订单的状态和 支付方式查询订单的总金额
	 */
	@Override
	public Double querySumprice(Integer status, Integer payMethod) {
		return payindentRepository.querySumprice(status, payMethod);
	}
	
	
}
