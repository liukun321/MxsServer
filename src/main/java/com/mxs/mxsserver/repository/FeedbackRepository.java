package com.mxs.mxsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mxs.mxsserver.domain.CoffeeInfo;
import com.mxs.mxsserver.domain.WorkerFeedback;
@Repository
public interface FeedbackRepository extends JpaRepository<WorkerFeedback, Integer> {


}
