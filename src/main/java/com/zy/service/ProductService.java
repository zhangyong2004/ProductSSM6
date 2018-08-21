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
		//1.��Ʒ�� ���Ʒ��Ϣ
		Product product=dao.queryProduct(id);
		
		//2.�м��+�����  ���ݵ�ǰ��Ʒ��id��ѯ����
		String pid = product.getId();
		List<Category> categories = queryCategories(pid);
		product.setCategories(categories);
		
		return product;
	}
	
	public List<Product> queryProducts(){
		//1.��Ʒ�� ���Ʒ��Ϣ
		List<Product> products=dao.queryProducts();
		
		//2.�м��+�����  ���ݵ�ǰ��Ʒ��id��ѯ����
		for(Product product:products) {
			String pid=product.getId();
			List<Category> categories = queryCategories(pid);
			product.setCategories(categories);			
		}
		
		return products;
	}
	
	public int deleteProduct(String id) {
		//1.�м�� ���ݵ�ǰ��Ʒ��idɾ������
		dao.deleteProductCategory(id);
		
		//2.��Ʒ�� ���ݵ�ǰ��Ʒ��idɾ����Ʒ
		return dao.deleteProduct(id);
	}
	
	public int insertProduct(Product product) {
		//1.��Ʒ��   ��ǰ��Ʒ���룬�Զ����ɲ�Ʒid
		dao.insertProduct(product);
		
		//2.�м��   �ѵ�ǰ��Ʒ�ķ������ݲ���
		String[] cids=product.getCids().split(",");
		String pid=product.getId();
		Map<String, Object> map=new HashMap<>();
		map.put("cids", cids);
		map.put("pid", pid);
		int result = dao.insertProductCategory(map);
		
		return result;
	}
	
	public int updateProduct(Product product) {
		//1.�м��   �ѵ�ǰ��Ʒ��ǰ�ķ�������ɾ��
		dao.deleteProductCategory(product.getId());

		//2.�м��   �ѵ�ǰ��Ʒ���µķ������ݲ���
		String[] cids=product.getCids().split(",");
		String pid=product.getId();
		Map<String, Object> map=new HashMap<>();
		map.put("cids", cids);
		map.put("pid", pid);
		dao.insertProductCategory(map);
		
		//3.��Ʒ��   �޸ĵ�ǰ��Ʒ����
		int result=dao.updateProduct(product);
		
		return result;
	}
	
	//�����   �����з���
	public List<Category> queryAllCategories(){
		return dao.queryAllCategories();
	}
	
	//�м��+�����  ���ݲ�Ʒid�鵱ǰ��Ʒ�ķ���
	public List<Category> queryCategories(String pid){
		return dao.queryCategories(pid);
	}
}
