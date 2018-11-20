package com.agent.repositories;


import com.agent.entities.Company;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompanyRepository extends PagingAndSortingRepository<Company, Integer> {

}
