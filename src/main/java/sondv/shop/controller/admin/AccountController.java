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
import sondv.shop.domain.Account;
import sondv.shop.model.AccountDto;
import sondv.shop.service.AccountService;

@Controller
@RequestMapping("admin/accounts")
public class AccountController {

	@Autowired
	AccountService accountService;
	

	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("account", new AccountDto());
		
		
		
		return "admin/accounts/addOrEdit";
	}
	
	
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute Account dto, BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/accounts/addOrEdit");
		}
		Account entity = new Account();

		BeanUtils.copyProperties(dto, entity);
		accountService.save(entity);
		model.addAttribute("message", "Account is saved!");
		return new ModelAndView("forward:/admin/accounts", model);
	}
	
	
	
	@RequestMapping("")
	public String list(ModelMap model) {

		List<Account> list = accountService.findAll();
		model.addAttribute("accounts", list);

		return "admin/accounts/list";
	}

	

//	@GetMapping("edit/{categoryId}")
//	public String edit() {
//		return "admin/accounts/addOrEdit";
//	}
//
//	@GetMapping("delete/{categoryId}")
//	public String delete() {
//		return "redirect:/admin/accounts";
//	}
//

//
//	@GetMapping("search")
//	public String search() {
//		return "admin/accounts/search";
//	}
}
