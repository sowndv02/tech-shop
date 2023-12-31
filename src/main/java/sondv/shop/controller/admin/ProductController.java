package sondv.shop.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import groovyjarjarantlr4.v4.Tool.Option;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import sondv.shop.domain.Category;
import sondv.shop.domain.Product;
import sondv.shop.model.CategoryDto;
import sondv.shop.model.ProductDto;
import sondv.shop.service.CategoryService;
import sondv.shop.service.ProductService;
import sondv.shop.service.StorageService;

@Controller
@RequestMapping("admin/products")
public class ProductController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	ProductService productService;

	@Autowired
	StorageService storageService;

	@ModelAttribute("categories")
	public List<CategoryDto> getCategories() {
		return categoryService.findAll().stream().map(item -> {
			CategoryDto dto = new CategoryDto();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).toList();
	}

	@GetMapping("add")
	public String add(Model model) {
		ProductDto dto = new ProductDto();
		dto.setIsEdit(false);
		model.addAttribute("product", dto);

		return "admin/products/addOrEdit";
	}

	@GetMapping("edit/{productId}")
	public ModelAndView edit(ModelMap model, @PathVariable("productId") Integer productId) {

		Optional<Product> opt = productService.findById(productId);

		ProductDto dto = new ProductDto();

		if (opt.isPresent()) {
			Product entity = opt.get();
			
			BeanUtils.copyProperties(entity, dto);

			dto.setCategoryId(entity.getCategory().getCategoryId());
			dto.setIsEdit(true);

			model.addAttribute("product", dto);
			return new ModelAndView("admin/products/addOrEdit", model);
		}
		model.addAttribute("message", "Product is not existed!");
		return new ModelAndView("forward:/admin/products", model);
	}

	@GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename){
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + file.getFilename() +"\"").body(file);
	}

	@GetMapping("delete/{productId}")
	public ModelAndView delete(ModelMap model, @PathVariable("productId") Integer productId) {


		Optional opt = productService.findById(productId);
		if(opt.isPresent()){
			if(!StringUtils.isEmpty(((Product) opt.get()).getImage())){
				storageService.delete(((Product) opt.get()).getImage());
			}
			productService.delete((Product) opt.get());
			model.addAttribute("message", "Product is deleted!");
		}else{
			model.addAttribute("message", "Product is not found!");
		}
		
		return new ModelAndView("forward:/admin/products", model) ;
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("product") ProductDto dto,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/products/addOrEdit");
		}

		Product entity = new Product();
		BeanUtils.copyProperties(dto, entity);

		Category category = new Category();
		category.setCategoryId(dto.getCategoryId());
		entity.setCategory(category);

		if (!dto.getImageFile().isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String uuString = uuid.toString();
			entity.setImage(storageService.getStorageFilename(dto.getImageFile(), uuString));
			storageService.store(dto.getImageFile(), entity.getImage());
		}

		productService.save(entity);
		model.addAttribute("message", "Product is saved!");
		return new ModelAndView("forward:/admin/products", model);
	}

	@RequestMapping("")
	public String list(ModelMap model) {

		List<Product> list = productService.findAll();
		model.addAttribute("products", list);

		return "admin/products/list";
	}

	@GetMapping("search")
	public String search() {
		return "admin/products/search";
	}
}
