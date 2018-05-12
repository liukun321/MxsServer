package com.mxs.mxsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mxs.mxsserver.domain.RollbackIndent;
@Repository
public interface RollbackIndentRepository extends JpaRepository<RollbackIndent, String> {

}
