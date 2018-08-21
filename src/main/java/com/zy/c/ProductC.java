package com.zy.c;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zy.domain.Category;
import com.zy.domain.Product;
import com.zy.service.ProductService;

@Controller
public class ProductC {
	
	@Autowired
	private ProductService service;

	@RequestMapping("/queryProducts.do")
	public ModelAndView queryProducts() {
		
		//1.��ȡ����
		//2.����ҵ��
		List<Product> products=service.queryProducts();

		//3.���ش�����
		ModelAndView mv=new ModelAndView();
		mv.addObject("products",products);
		mv.setViewName("/product-list");
		return mv;
	}
	
	@RequestMapping("/deleteProduct.do")
	public ModelAndView deleteProduct(String id) {
		
		//1.��ȡ����
		System.out.println(id);
		//2.����ҵ��
		int result=service.deleteProduct(id); 
		
		//3.���ش�����
		ModelAndView mv=new ModelAndView();
		if(result>0) {
			mv.setViewName("redirect:/queryProducts.do");
		}else {
			mv.setViewName("/fail");
		}
		
		return mv;
	}
	
	@RequestMapping("/insertProduct.do")
	public ModelAndView insertProduct(Product product) {
		//1.��ȡ����
		System.out.println(product);
		
		//2.����ҵ��
		int result = service.insertProduct(product);
		
		//3.���ش�����
		ModelAndView mv=new ModelAndView();
		if(result>0) {
			mv.setViewName("redirect:/queryProducts.do");
		}else{
			mv.setViewName("/fail");
		}
		
		
		return mv;
	}
	
	@RequestMapping("/updateProduct.do")
	public ModelAndView updateProduct(Product product) {
		
		//1.��ȡ����
		System.out.println(product);
		//2.����ҵ��
		int result=service.updateProduct(product);
		
		//3.���ش�����
		ModelAndView mv=new ModelAndView();
		if(result>0) {
			mv.setViewName("redirect:/queryProducts.do");
		}else {
			mv.setViewName("/fail");
		}
		
		return mv;
	}
	
	@RequestMapping("/getUpdateForm.do")
	public ModelAndView getUpdateForm(String id) {
		//1.��ȡ����
		System.out.println(id);
		
		//2.����ҵ��
		Product product=service.queryProduct(id);
		List<Category> categories=service.queryAllCategories();
		
		//3.���ش�����
		ModelAndView mv=new ModelAndView();
		mv.addObject("product",product);
		mv.addObject("categories",categories);
		mv.setViewName("/update-form");
		
		return mv;
	}
	
	@RequestMapping("/getInsertForm.do")
	public ModelAndView getInsertForm() {
		//1.��ȡ����
		//2.����ҵ��
		List<Category> categories=service.queryAllCategories();
		
		//3.���ش�����
		ModelAndView mv=new ModelAndView();
		mv.addObject("categories",categories);
		mv.setViewName("/insert-form");
		
		return mv;
	}
}
