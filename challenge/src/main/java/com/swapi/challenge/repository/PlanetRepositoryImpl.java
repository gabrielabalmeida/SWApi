package com.swapi.challenge.repository;

import com.swapi.challenge.model.Planet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PlanetRepositoryImpl implements PlanetRepository{

    @Autowired
    private DynamoDbAsyncClient dynamoDbAsyncClient;

    private final String tableName = "planets";

    @Override
    public Mono<Planet> findByName(String name) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("name", AttributeValue.builder().s(name).build());
        return Mono.fromFuture(this.dynamoDbAsyncClient
                .getItem(GetItemRequest
                        .builder()
                        .tableName(this.tableName)
                        .key(key)
                        .build())
                .thenApplyAsync(GetItemResponse::item)
                .thenApply(item -> item.isEmpty() ? null : new Planet(item)));
    }

    @Override
    public Flux<Planet> findAll() {
        return Mono.fromFuture(this.dynamoDbAsyncClient.scan(ScanRequest.builder().tableName(this.tableName).build())
                .thenApplyAsync(ScanResponse::items)
                .thenApply(item -> item.stream()
                        .map(Planet::new)
                        .collect(Collectors.toList()))).flatMapMany(Flux::fromIterable);
    }

    @Override
    public Mono<Planet> findById(String uuid) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("uuid", AttributeValue.builder().s(uuid).build());
        return Mono.fromFuture(this.dynamoDbAsyncClient
                .getItem(GetItemRequest
                        .builder()
                        .tableName(this.tableName)
                        .key(key)
                        .build())
                .thenApplyAsync(GetItemResponse::item)
                .thenApply(item -> item.isEmpty() ? null : new Planet(item)));
    }

    @Override
    public Mono<Planet> save(Planet planet) {
        return Mono.fromFuture(this.dynamoDbAsyncClient
                .putItem(PutItemRequest.builder()
                        .tableName(this.tableName)
                        .item(planet.mapper())
                        .build()).thenApplyAsync(PutItemResponse::attributes)
                .thenApply(plt -> planet));
    }

    @Override
    public void deleteById(String uuid) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("uuid", AttributeValue.builder().s(uuid).build());
         this.dynamoDbAsyncClient
        .deleteItem(DeleteItemRequest.builder()
                .tableName(this.tableName)
                .key(key)
                .build());
    }

    @Override
    public void deleteAll() {
        this.dynamoDbAsyncClient
                .deleteTable(DeleteTableRequest.builder()
                        .tableName(this.tableName)
                        .build());
    }


}
