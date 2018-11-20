package com.agent.controllers;

import com.agent.entities.Company;
import com.agent.services.CompanyService;
import com.agent.util.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * List all companies
     */

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, Pageable pageable){

        Page<Company> companyPage = companyService.listAllByPage(pageable);
        PageWrapper<Company> page = new PageWrapper<>(companyPage, "/companies");
        model.addAttribute("companies", page.getContent());
        model.addAttribute("page", page);
        return "companies";
    }

    /**
     * Edit company.
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        Company company = companyService.getCompanyById(id);
        model.addAttribute("company", company);
        return "companyform";
    }

    /**
     * New company.
     */
    @RequestMapping("/new")
    public String newCompany(Model model) {

        model.addAttribute("company", new Company());
        return "companyform";
    }

    /**
     * Save company to database.
     */
    @RequestMapping(value = "/company", method = RequestMethod.POST)
    public String saveCompany(@Valid Company company, BindingResult bindingResult, Model model, Principal principal) {

        if(validate(company, bindingResult))
        {
            model.addAttribute("company", company);
            return "companyform";
        }
        companyService.saveCompany(company);
        return "redirect:/companies";
    }

    /**
     * Delete company by its id.
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Principal principal) {

        Company company = companyService.getCompanyById(id);
        companyService.deleteCompany(company);

        return "redirect:/companies";
    }

    private boolean validate(@Valid Company company, BindingResult bindingResult) {

        boolean hasError = false;
        if (bindingResult.hasErrors()) {
            hasError = true;
        }
        else
        {
            /*
            if(companyService.(user))
            {
                hasError = true;
                bindingResult.rejectValue("name", "error.name", "This Username already exists");
            }
            */
        }
        return hasError;
    }

}
