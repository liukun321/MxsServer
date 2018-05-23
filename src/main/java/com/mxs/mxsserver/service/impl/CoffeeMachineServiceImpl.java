package com.mxs.mxsserver.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxs.mxsserver.domain.CoffeeMachine;
import com.mxs.mxsserver.repository.CoffeeMachineRepository;
import com.mxs.mxsserver.service.CoffeeMachineService;

/**
 * 
 * @author liukun
 *
 */
@Service
public class CoffeeMachineServiceImpl implements CoffeeMachineService{
	private final Logger log = LoggerFactory.getLogger(CoffeeMachineServiceImpl.class);
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	private CoffeeMachineRepository coffeeMachineRepository;
	
	public CoffeeMachineServiceImpl(CoffeeMachineRepository coffeeMachineRepository) {
		super();
		this.coffeeMachineRepository = coffeeMachineRepository;
	}

	@Override
	public CoffeeMachine getCoffeeMachineInfo(String venderName) {
		return coffeeMachineRepository.findOne(venderName);
	}

	@Override
	public List<CoffeeMachine> machineForEmployee(String employeeCode) {
		return coffeeMachineRepository.findByEmployeeCode(employeeCode);
//		return null;
	}

	@Override
	public List<CoffeeMachine> getAllCoffeeMachine() {
		return coffeeMachineRepository.findAll();
	}

	@Override
	public CoffeeMachine addCoffeeMachine(CoffeeMachine coffeeMachine) {
		return coffeeMachineRepository.save(coffeeMachine);
	}
	@Transactional
	@Override
	public List<CoffeeMachine> queryMachinesByCode(List<String> machineCodes) {
		List<CoffeeMachine> list = new ArrayList();
		for (String code : machineCodes) {
			CoffeeMachine cm = this.getCoffeeMachineInfo(code);
			if(null != cm) {
				list.add(cm);
			}
		}
		return list;
	}
	@Transactional
	@Override
	public void addCoffeeMachines(List<CoffeeMachine> list) {
		//批量保存，数量过多会出现问题，这里认为一个运维人员负责的机器数量较小
		//1 循环将CoffeeMachine实例放进entityManager中，注意这里的CoffeeMachine实例的id属性必须为空
		try {
			list.stream().forEach(machine -> entityManager.merge(machine));
		}catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		//2 刷新后一次两所有的实例保存到数据库中 
		entityManager.flush();
	}
	/**
	 * 查找所有没有绑定运营人员的咖啡机
	 */
	@Override
	public List<CoffeeMachine> getAllNoEmployee() {
		List<CoffeeMachine> list = coffeeMachineRepository.findByEmployeeCodeIsNull();
		return list;
	}
	
}
