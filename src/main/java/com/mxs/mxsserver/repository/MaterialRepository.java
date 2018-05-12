package com.mxs.mxsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mxs.mxsserver.domain.Material;
@Repository
public interface MaterialRepository extends JpaRepository<Material, String> {

	Material findByMachineId(String venderName);

}
