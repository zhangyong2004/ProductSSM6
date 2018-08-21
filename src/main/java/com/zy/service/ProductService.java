package com.zy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zy.dao.ProductDao;
import com.zy.domain.Category;
import com.zy.domain.Product;

@Service
public class ProductService {

	@Autowired
	private ProductDao dao;
	
	public Product queryProduct(String id) {
		//1.产品表 查产品信息
		Product product=dao.queryProduct(id);
		
		//2.中间表+分类表  根据当前产品的id查询分类
		String pid = product.getId();
		List<Category> categories = queryCategories(pid);
		product.setCategories(categories);
		
		return product;
	}
	
	public List<Product> queryProducts(){
		//1.产品表 查产品信息
		List<Product> products=dao.queryProducts();
		
		//2.中间表+分类表  根据当前产品的id查询分类
		for(Product product:products) {
			String pid=product.getId();
			List<Category> categories = queryCategories(pid);
			product.setCategories(categories);			
		}
		
		return products;
	}
	
	public int deleteProduct(String id) {
		//1.中间表 根据当前产品的id删除分类
		dao.deleteProductCategory(id);
		
		//2.产品表 根据当前产品的id删除产品
		return dao.deleteProduct(id);
	}
	
	public int insertProduct(Product product) {
		//1.产品表   当前产品插入，自动生成产品id
		dao.insertProduct(product);
		
		//2.中间表   把当前产品的分类数据插入
		String[] cids=product.getCids().split(",");
		String pid=product.getId();
		Map<String, Object> map=new HashMap<>();
		map.put("cids", cids);
		map.put("pid", pid);
		int result = dao.insertProductCategory(map);
		
		return result;
	}
	
	public int updateProduct(Product product) {
		//1.中间表   把当前产品以前的分类数据删掉
		dao.deleteProductCategory(product.getId());

		//2.中间表   把当前产品的新的分类数据插入
		String[] cids=product.getCids().split(",");
		String pid=product.getId();
		Map<String, Object> map=new HashMap<>();
		map.put("cids", cids);
		map.put("pid", pid);
		dao.insertProductCategory(map);
		
		//3.产品表   修改当前产品数据
		int result=dao.updateProduct(product);
		
		return result;
	}
	
	//分类表   查所有分类
	public List<Category> queryAllCategories(){
		return dao.queryAllCategories();
	}
	
	//中间表+分类表  根据产品id查当前产品的分类
	public List<Category> queryCategories(String pid){
		return dao.queryCategories(pid);
	}
}
