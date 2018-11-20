package com.agent.services;

import com.agent.entities.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CompanyService {

    Page<Company> listAllByPage(Pageable pageable);

    Iterable<Company> listAll();

    Company getCompanyById(Integer id);

    Company saveCompany(Company company);

    void deleteCompany(Company company);
}
