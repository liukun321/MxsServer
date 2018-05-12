package com.mxs.mxsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mxs.mxsserver.domain.CoffeeMaterial;


@Repository
public interface CoffeeMaterialRepository extends JpaRepository<CoffeeMaterial, Integer>{

}
