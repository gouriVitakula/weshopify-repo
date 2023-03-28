package com.weshopify.platform.service;

import java.util.List;

import com.weshopify.platform.bean.BrandsBean;


public interface BrandsService {

	BrandsBean createBrand(BrandsBean catBean);
	BrandsBean updateBrand(BrandsBean catBean);
	
	List<BrandsBean> findAllBrands();
	BrandsBean findBrandById(int brandId);
	
	List<BrandsBean> deleteBrand(int brandId);
	void cleanDb();
	
}
