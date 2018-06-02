package com.mxs.mxsserver.service;

import java.util.List;

import com.mxs.mxsserver.domain.CoffeeMaterial;

public interface CoffeeMaterialService {
	public void addMaterial(CoffeeMaterial material);
	/**
	 * 根据咖啡机Id查询该咖啡机的所有物料信息
	 * @param machineId
	 * @return
	 */
	public List<CoffeeMaterial> queryMaterialByMachineId(String machineId);
	/**
	 * 根据 咖啡机Id和物料盒的编号查询指定的料盒信息
	 * @param machineId
	 * @param number
	 * @return
	 */
	public CoffeeMaterial queryMaterialByIdAndNumber(String machineId, Integer number);
	
	public void batchInsertMaterial(List<CoffeeMaterial> list);
	/**
	 * 根据咖啡机Id和料盒状态进行查询
	 * @param machineId
	 * @param status
	 * @return
	 */
	public List<CoffeeMaterial> queryMaterialByStatus(String machineId, Integer status);
}
