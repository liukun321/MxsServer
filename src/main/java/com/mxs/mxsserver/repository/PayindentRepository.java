package com.mxs.mxsserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mxs.mxsserver.domain.Payindent;
@Repository
public interface PayindentRepository extends JpaRepository<Payindent, String> {

	Payindent findByIndentId(String indentId);

	List<Payindent> findByPayStatus(Integer status);
	@Query("SELECT SUM(p.price) FROM Payindent p WHERE p.payStatus=?1 and p.payMethod = ?2")
	Double querySumprice(Integer status, Integer payMethod);

}
