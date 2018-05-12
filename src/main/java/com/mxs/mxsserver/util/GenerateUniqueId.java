package com.mxs.mxsserver.util;

/**
 * 产生唯一的ID
 * @author liukun
 *
 */
public class GenerateUniqueId {
	/**
	 * 生成咖啡产品的ID
	 * @return
	 */
	public static Integer generateCoffeeId() {
		return RandomStringGenerator.getRandomInteger(8);
	}
	
	public static String generateMachineId() {
		return RandomStringGenerator.getRandomString(15);
	}
}
