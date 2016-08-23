package controllers;

/**
 * Created by jg.murillo10 on 22/08/2016.
 */

import dispatchers.AkkaDispatcher;
import java.util.concurrent.CompletableFuture;
import static play.libs.Json.toJson;
import models.ProductEntity;
import akka.dispatch.MessageDispatcher;
import models.WishListEntity;
import play.mvc.*;

import java.util.concurrent.CompletionStage;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;

public class WishListController extends Controller {



    public CompletionStage<Result> getWishLists() {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(
                        () -> {
                            return WishListEntity.FINDER.all();
                        }
                        ,jdbcDispatcher)
                .thenApply(
                        wishListEntities -> {
                            return ok(toJson(wishListEntities));
                        }
                );
    }

    public CompletionStage<Result> getWishList(Long id){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.
                supplyAsync(
                        () -> {
                            return WishListEntity.FINDER.byId(id);
                        }
                        ,jdbcDispatcher)
                .thenApply(
                        wishListEntity -> {
                            return ok(toJson(wishListEntity));
                        }
                );

    }
    public CompletionStage<Result> createWishList(){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nWishList = request().body().asJson();
        WishListEntity wishList = Json.fromJson( nWishList , WishListEntity.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    wishList.save();
                    return wishList;
                }
        ).thenApply(
                wishListEntity -> {
                    return ok(Json.toJson(wishListEntity));
                }
        );
    }
    public CompletionStage<Result> deleteWishList(Long id){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        WishListEntity wishList= WishListEntity.FINDER.byId(id);
        return CompletableFuture.supplyAsync(
                ()->{
                    wishList.delete();

                    return wishList;
                }
        ).thenApply(
                wishListEntity -> {
                    return ok(Json.toJson(wishListEntity));
                }
        );

    }
    public CompletionStage<Result> updateWishList(Long id){

        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nWishList = request().body().asJson();
        WishListEntity newWishList = Json.fromJson( nWishList , WishListEntity.class ) ;
        WishListEntity actual=WishListEntity.FINDER.byId(id);


        return CompletableFuture.supplyAsync(
                ()->{
                    actual.update(newWishList);
                    actual.update();
                    return actual;
                }
        ).thenApply(
                wishListEntity -> {
                    return ok(Json.toJson(wishListEntity));
                }
        );

    }


}
