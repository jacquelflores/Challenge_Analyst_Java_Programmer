package com.example.ApiRest_Tarea.repository;

import com.example.ApiRest_Tarea.model.entity.CountryInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositoryInfoCountry extends MongoRepository<CountryInfo, String> {
}
