package com.agent.api;

import com.agent.entities.Company;
import com.agent.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;


@RestController
@RequestMapping("/api/companies")
@PreAuthorize("hasRole('ADMIN')")
public class CompanyRestController {

    @Autowired
    private CompanyService companyService;

    /**
     * List all companies
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Company>> listAllCompanies() {
        List<Company> companies = (List<Company>) companyService.listAll();
        if(companies.isEmpty()){
            return new ResponseEntity<>(companies, HttpStatus.NO_CONTENT);//or HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    /**
     * Get company by id
     */

    @RequestMapping(value = "/company/{id}", method = RequestMethod.GET)
    public ResponseEntity<Company> getCompany(@PathVariable("id") Integer id) {
        Company company = companyService.getCompanyById(id);
        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    /**
     * Create company
     */

    @RequestMapping(value = "/company/", method = RequestMethod.POST)
    public ResponseEntity<Void> createCompany(@RequestBody Company company, UriComponentsBuilder ucBuilder) {
        companyService.saveCompany(company);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/company/{id}").buildAndExpand(company.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Update company
     */

    @RequestMapping(value = "/company/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Company> updateCompany(@PathVariable("id") Integer id, @RequestBody Company company) {
        Company currentCompany = companyService.getCompanyById(id);
        if (currentCompany == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentCompany.setName(company.getName());
        currentCompany.setWebsite(company.getWebsite());
        companyService.saveCompany(currentCompany);
        return new ResponseEntity<>(currentCompany, HttpStatus.OK);
    }

    /**
     * Delete company
     */

    @RequestMapping(value = "/company/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Company> deleteCompany(@PathVariable("id") Integer id) {
        Company company = companyService.getCompanyById(id);
        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        companyService.deleteCompany(company);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
