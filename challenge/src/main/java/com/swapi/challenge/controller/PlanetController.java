package com.swapi.challenge.controller;

import com.swapi.challenge.config.DynamoDBConfig;
import com.swapi.challenge.controller.request.PlanetRequest;
import com.swapi.challenge.controller.response.HealthResponse;
import com.swapi.challenge.controller.response.PlanetResponse;
import com.swapi.challenge.model.Planet;
import com.swapi.challenge.services.PlanetServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.model.*;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    private Logger logger = LoggerFactory.getLogger(PlanetController.class);

    private final PlanetServices planetServices;

    @Autowired
    private DynamoDBConfig dynamoDBConfig;

    public PlanetController(PlanetServices planetServices) {
        this.planetServices = planetServices;
    }

    @GetMapping
    public @ResponseBody ResponseEntity<?> getAll() {
        return ResponseEntity.ok(this.planetServices
                .getAll()
                .doOnComplete(() -> {
                    this.logger.info("Finalizou a busca de planetas.");
                }));
    }

    @PostMapping
    public @ResponseBody ResponseEntity<?> createPlanets(@RequestBody PlanetRequest planets) {
        return ResponseEntity.ok(this.planetServices
                .addPlanet(planets.toModel()));
//                .flatMap(planet1 -> {
//                    //UriBuilder
//                    return Mono.justOrEmpty(ResponseEntity.ok(new PlanetResponse(planet1)));
//                }).doOnSuccess(planetResponseEntity -> {
//                    this.logger.info("Planeta inserido com sucesso: [{}] ", planetResponseEntity);
//                }).doOnError(err -> {
//                    this.logger.error("Falha ao inserir o planeta: ", err);
//                });
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody ResponseEntity<?> deletePlanets(@PathVariable(name = "id") String id) {
        this.planetServices.removePlanetById(id);

        return new ResponseEntity<>(id + " deletado",HttpStatus.OK);
    }

    @DeleteMapping
    public @ResponseBody ResponseEntity<?> deleteAll() {
        this.planetServices.removeAll();

        return new ResponseEntity<>("Deletado",HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public Mono<ResponseEntity<PlanetResponse>> updatePlanet(@PathVariable String id, @Valid @RequestBody PlanetRequest planet) {

        return this.planetServices
                .updatePlanet(id,planet)
                .map(PlanetResponse::new)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .next()
                .doOnSuccess(planetResponseEntity -> {
                    this.logger.info("Planeta atualizado com sucesso: [{}] ", planetResponseEntity);
                }).doOnError(err -> {
                    this.logger.info("Falha ao atualizar o planeta: ", err);
                });
    }

    @PostMapping(path = "/findId/{uuid}")
    public @ResponseBody Mono<ResponseEntity<PlanetResponse>> searchById(@PathVariable(name = "uuid") String uuid) {

        return this.planetServices
                .findById(uuid)
                .map(PlanetResponse::new)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .doOnSuccess(planetResponseEntity -> {
                    this.logger.info("Planeta atualizado com sucesso: [{}] ", planetResponseEntity);
                }).doOnError(err -> {
                    this.logger.info("Falha ao atualizar o planeta: ", err);
                });
    }

    @PostMapping(path = "/findName/{name}")
    public @ResponseBody ResponseEntity<?> searchByName(@PathVariable(name = "name") String name) {

        return ResponseEntity.ok(this.planetServices
                .findByName(name)
                .flatMap(planet1 -> Flux.just(ResponseEntity.ok(new PlanetResponse(planet1)))));
    }


    @GetMapping("/health")
    public Mono<ResponseEntity> health() throws ExecutionException, InterruptedException {
        this.dynamoDBConfig.getDynamoAsyncClient().createTable(CreateTableRequest.builder()
                .tableName("planets")
                .keySchema(KeySchemaElement.builder()
                        .attributeName("uuid").keyType(KeyType.HASH)
                        .build())
                .attributeDefinitions(AttributeDefinition.builder().attributeName("uuid").attributeType(ScalarAttributeType.S).build())
                .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(10l).writeCapacityUnits(10l).build())
                .build());
        return Mono.just(new HealthResponse()).map(ResponseEntity::ok);
    }
}