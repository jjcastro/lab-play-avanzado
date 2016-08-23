package controllers;

import dispatchers.AkkaDispatcher;
import java.util.concurrent.CompletableFuture;
import static play.libs.Json.toJson;
import models.ItemEntity;
import akka.dispatch.MessageDispatcher;
import play.mvc.*;

import java.util.concurrent.CompletionStage;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by jg.murillo10 on 20/08/2016.
 */
public class ItemController extends Controller {



    public CompletionStage<Result> getItems() {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(
                        () -> {
                            return ItemEntity.FINDER.all();
                        }
                        ,jdbcDispatcher)
                .thenApply(
                        itemEntities -> {
                            return ok(toJson(itemEntities));
                        }
                );
    }

    public CompletionStage<Result> getItem(Long id){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.
                supplyAsync(
                        () -> {
                            return ItemEntity.FINDER.byId(id);
                        }
                        ,jdbcDispatcher)
                .thenApply(
                        itemEntity -> {
                            return ok(toJson(itemEntity));
                        }
                );

    }

    public CompletionStage<Result> deleteItem(Long id) {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        ItemEntity item = ItemEntity.FINDER.byId(id);
        return CompletableFuture.supplyAsync(
                () -> {
                    item.delete();
                    return item;
                }, jdbcDispatcher
        ).thenApply(
                itemEntity -> {
                    return ok(Json.toJson(itemEntity));
                }
        );
    }


    public CompletionStage<Result> createItem(){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nItem = request().body().asJson();
        ItemEntity item = Json.fromJson( nItem , ItemEntity.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    item.save();
                    return item;
                }, jdbcDispatcher
        ).thenApply(
                itemEntity -> {
                    return ok(Json.toJson(itemEntity));
                }
        );
    }


    public CompletionStage<Result> updateItem(Long id){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nItem = request().body().asJson();
        ItemEntity newItem = Json.fromJson( nItem , ItemEntity.class ) ;
        ItemEntity old=ItemEntity.FINDER.byId(id);
        return CompletableFuture.supplyAsync(
                ()->{
                    old.update(newItem);
                    old.update();
                    return old;
                },jdbcDispatcher
        ).thenApply(
                itemEntity -> {
                    return ok(Json.toJson(itemEntity));
                }
        );
    }

}
