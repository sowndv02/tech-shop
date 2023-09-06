package sondv.shop.controller.admin;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import sondv.shop.domain.Category;
import sondv.shop.model.CategoryDto;
import sondv.shop.service.CategoryService;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("category", new CategoryDto());
		
		 return "admin/categories/addOrEdit";
	}
	
	@GetMapping("edit/{categoryId}")
	public String edit() {
		return "admin/categories/addOrEdit";
	}

	@GetMapping("delete/{categoryId}")
	public String delete() {
		return "redirect:/admin/categories";
	}
	
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, 
			@Valid @ModelAttribute Category dto,
			BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/categories/addOrEdit");
		}
		Category entity = new Category();
		
		BeanUtils.copyProperties(dto, entity);
		categoryService.save(entity);
		model.addAttribute("message", "Category is saved!");
		return new ModelAndView("forward:/admin/categories", model);
	}
	
	@RequestMapping("")
	public String list(ModelMap model) {
		
		List<Category> list = categoryService.findAll();
		model.addAttribute("categories", list);
		
		return "admin/categories/list";
	}
	
	@GetMapping("search")
	public String search() {
		return "admin/categories/search";
	}
}
