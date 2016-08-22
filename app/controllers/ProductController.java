package controllers;

/**
 * Created by jg.murillo10 on 20/08/2016.
 */

import dispatchers.AkkaDispatcher;
import java.util.concurrent.CompletableFuture;
import static play.libs.Json.toJson;
import models.ProductEntity;
import akka.dispatch.MessageDispatcher;
import play.mvc.*;

import java.util.concurrent.CompletionStage;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;
public class ProductController extends Controller {



    public CompletionStage<Result> getProducts() {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(
                        () -> {
                            return ProductEntity.FINDER.all();
                        }
                        ,jdbcDispatcher)
                .thenApply(
                        productEntities -> {
                            return ok(toJson(productEntities));
                        }
                );
    }
    public CompletionStage<Result> getProduct(){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        String nProduct = request().path().toString();
        return CompletableFuture.
                supplyAsync(
                        () -> {
                            return ProductEntity.FINDER.all();
                        }
                        ,jdbcDispatcher)
                .thenApply(
                        productEntity -> {
                            return ok(toJson(productEntity));
                        }
                );

    }
    public CompletionStage<Result> deleteProduct(){
        return null;
    }


    public CompletionStage<Result> createProduct(){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nProduct = request().body().asJson();
        ProductEntity product = Json.fromJson( nProduct , ProductEntity.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    product.save();
                    return product;
                }
        ).thenApply(
                productEntity -> {
                    return ok(Json.toJson(productEntity));
                }
        );
    }



}
