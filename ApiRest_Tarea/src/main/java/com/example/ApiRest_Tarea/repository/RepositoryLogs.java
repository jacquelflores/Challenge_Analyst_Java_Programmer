package com.example.ApiRest_Tarea.repository;

import com.example.ApiRest_Tarea.model.entity.Logs;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface RepositoryLogs extends MongoRepository<Logs, String>{
}
