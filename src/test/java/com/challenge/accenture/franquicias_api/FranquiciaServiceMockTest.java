package com.challenge.accenture.franquicias_api;

import com.challenge.accenture.franquicias_api.domain.model.Franquicia;
import com.challenge.accenture.franquicias_api.infrastructure.repository.FranquiciaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class FranquiciaServiceMockTest {

    @Test
    void mockRepositoryTest() {

        FranquiciaRepository repo = Mockito.mock(FranquiciaRepository.class);

        Franquicia franquicia = Franquicia.builder()
                .id("1")
                .nombre("Mock")
                .build();

        Mockito.when(repo.findById("1"))
                .thenReturn(Mono.just(franquicia));

        StepVerifier.create(repo.findById("1"))
                .expectNextMatches(f -> f.getNombre().equals("Mock"))
                .verifyComplete();
    }
}