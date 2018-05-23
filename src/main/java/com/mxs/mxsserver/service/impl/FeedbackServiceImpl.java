package com.mxs.mxsserver.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mxs.mxsserver.domain.WorkerFeedback;
import com.mxs.mxsserver.repository.FeedbackRepository;
import com.mxs.mxsserver.service.FeedbackService;
@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {
	
	private FeedbackRepository feedbackRepository;
	
	
	public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
		super();
		this.feedbackRepository = feedbackRepository;
	}


	@Override
	public WorkerFeedback addFeedback(WorkerFeedback back) {
		return feedbackRepository.save(back);
	}

}
