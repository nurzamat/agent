package com.agent.services.impl;

import com.agent.entities.Company;
import com.agent.repositories.CompanyRepository;
import com.agent.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    @Autowired
    public void setCompanyRepository(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    @Override
    public Page<Company> listAllByPage(Pageable pageable)
    {
        return companyRepository.findAll(pageable);
    }

    @Override
    public Iterable<Company> listAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Integer id)
    {
        return companyRepository.findOne(id);
    }

    @Override
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(Company company) {
        companyRepository.delete(company);
    }
}
