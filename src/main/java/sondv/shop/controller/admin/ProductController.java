package sondv.shop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sondv.shop.domain.Category;
import sondv.shop.domain.Product;
import sondv.shop.model.CategoryDto;
import sondv.shop.model.ProductDto;
import sondv.shop.service.CategoryService;
import sondv.shop.service.ProductService;

@Controller
@RequestMapping("admin/products")
public class ProductController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@ModelAttribute("categories")
	public List<CategoryDto> getCategories(){
		return categoryService.findAll().stream().map(item -> {
			CategoryDto dto = new CategoryDto();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).toList();
	}
	
	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("product", new ProductDto());
		
		 return "admin/products/addOrEdit";
	}
	
	@GetMapping("edit/{productId}")
	public ModelAndView edit(ModelMap model, 
			@PathVariable("productId") Integer productId) {
		
		Optional<Product> opt = productService.findById(productId);
		ProductDto dto = new ProductDto();
		
		if(opt.isPresent()) {
			Product entity = opt.get();
			BeanUtils.copyProperties(entity, opt);
			model.addAttribute("product", dto);
			return new ModelAndView("admin/products/addOrEdit", model);
		}
		model.addAttribute("message", "Category is not existed!");
		return new ModelAndView("forward:/admin/products", model);
	}

	@GetMapping("delete/{productId}")
	public String delete() {
		return "redirect:/admin/products";
	}
	
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, CategoryDto dto) {
		Category entity = new Category();
		
		BeanUtils.copyProperties(dto, entity);
		
		categoryService.save(entity);
		model.addAttribute("message", "Category is saved!");
		return new ModelAndView("forward:/admin/products", model);
	}
	
	@RequestMapping("")
	public String list(ModelMap model) {
		
		List<Category> list = categoryService.findAll();
		model.addAttribute("products", list);
		
		return "admin/products/list";
	}
	
	@GetMapping("search")
	public String search() {
		return "admin/products/search";
	}
}
