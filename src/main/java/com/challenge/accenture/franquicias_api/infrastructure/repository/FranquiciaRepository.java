package com.challenge.accenture.franquicias_api.infrastructure.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.challenge.accenture.franquicias_api.domain.model.Franquicia;

@Repository
public interface FranquiciaRepository extends ReactiveMongoRepository<Franquicia, String> {
}