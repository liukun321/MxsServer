package com.mxs.mxsserver.service;

import com.mxs.mxsserver.domain.Material;

public interface MaterialService {
	Material updateMaterial(Material material);
	
	Material queryMaterial(String venderName);
}
