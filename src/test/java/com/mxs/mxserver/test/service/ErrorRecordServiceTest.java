package com.mxs.mxserver.test.service;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mxs.mxsserver.MxsServerApplication;
import com.mxs.mxsserver.domain.ErrorRecord;
import com.mxs.mxsserver.service.ErrorRecordService;

@RunWith(SpringRunner.class)  
@SpringBootTest(classes = {MxsServerApplication.class}) 
public class ErrorRecordServiceTest {
	@Autowired
	private ErrorRecordService errorRecordService;
	static CountDownLatch countDownLatch = new CountDownLatch(3);
	@Test
	public void test() {
		ErrorRecord error = new ErrorRecord();
		error.setMachineId("ad12324");
		error.setStartTime(new Date());
		error.setType(1);
		error.setWorkerId("10342441");
		error = errorRecordService.addErrorRecord(error);
//		System.out.println(error.toString());
//		
//		Thread th1 = new Thread(new Runnable() {
		//
//		@Override
//		public void run() {
//			ErrorRecord error = new ErrorRecord();
//			error.setType(1);
//			error.setMachineId("a123456");
//			error.setStartTime(new Date());
//			error.setSumError(1);
//			errorRecordRepository.save(error);
//			System.out.println(error.getType() + "---" + Thread.currentThread().getName());
//			countDownLatch.countDown();
//		}
//		
//	}, "11");
//	Thread th2 = new Thread(new Runnable() {
//
//		@Override
//		public void run() {
//			ErrorRecord error = errorRecordRepository.findByMachineIdAndEndTimeIsNull("a123456");
//			error.setType(2);
//			errorRecordRepository.save(error);
//			System.out.println(error.getType() + "---" + Thread.currentThread().getName());
//			countDownLatch.countDown();
//		}
//		
//	}, "22");
//	
//	Thread th3 = new Thread(new Runnable() {
//
//		@Override
//		public void run() {
//			ErrorRecord error = errorRecordRepository.findByMachineIdAndEndTimeIsNull("a123456");
//			error.setType(3);
//			errorRecordRepository.save(error);
//			System.out.println(error.getType() + "---" + Thread.currentThread().getName());
//			countDownLatch.countDown();
//		}
//	}, "33");
//	
//	th1.start();
//	th2.start();
//	th3.start();
	}
}
