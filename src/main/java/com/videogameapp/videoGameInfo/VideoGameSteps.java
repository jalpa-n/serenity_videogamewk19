package com.videogameapp.videoGameInfo;

import com.videogameapp.constants.EndPoints;
import com.videogameapp.model.VideoGamePojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.List;

public class VideoGameSteps {

    @Step("Get all video game ")
    public ValidatableResponse getAllVideoGame(){

        return SerenityRest.rest()
                .given()
                .header("Accept","application/json")
                .when()
                .get()
                .then();
    }

    @Step("Get single video game : {0}")
    public ValidatableResponse getSingleVideoGame(int videoGameId){

        return SerenityRest.rest()
                .given()
                .header("Accept","application/json")
                .pathParam("videoGameId",videoGameId)
                .when()
                .get(EndPoints.GET_SINGLE_VIDEOGAME_BY_ID)
                .then();
    }



    @Step("Creating video game  with name:{0}, releaseDate: {1}, reviewScore: {2}, rating: {3}")
    public ValidatableResponse createVideoGame( String name, String releaseDate,
                                               int reviewScore, String category, String rating){

        VideoGamePojo videoGamePojo=new VideoGamePojo();
        videoGamePojo.setName(name);
        videoGamePojo.setReleaseDate(releaseDate);
        videoGamePojo.setReviewScore(reviewScore);
        videoGamePojo.setCategory(category);
        videoGamePojo.setRating(rating);

        return   SerenityRest.rest()
                .given().log().all()
                .header("Content-Type", "application/json")
                .body(videoGamePojo)
                .when()
                .post()
                .then();

    }

    @Step("Update video game with name:{0}, releaseDate: {1}, reviewScore: {2}, rating: {3}")
    public ValidatableResponse updateVideoGame( int videoGameId,String name, String releaseDate,
                                                int reviewScore, String category, String rating){

        VideoGamePojo videoGamePojo=new VideoGamePojo();
        videoGamePojo.setName(name);
        videoGamePojo.setReleaseDate(releaseDate);
        videoGamePojo.setReviewScore(reviewScore);
        videoGamePojo.setCategory(category);
        videoGamePojo.setRating(rating);

        return   SerenityRest.rest()
                .given().log().all()
                .pathParam("videoGameId",videoGameId)
                .header("Content-Type", "application/json")
                .body(videoGamePojo)
                .when()
                .put(EndPoints.UPDATE_VIDEOGAME_BY_ID)
                .then();

    }

    @Step("Delete video game with id: {0}")
    public ValidatableResponse deleteVideoGameWithId(int videoGameId){

        return SerenityRest.rest()
                .given()
                .pathParam("videoGameId",videoGameId)
                .when()
                .delete(EndPoints.DELETE_VIDEOGAME_BY_ID)
                .then();
    }
    @Step("Create new video game with xml")
    public ValidatableResponse createVideoGameWithXml(String body){



        return SerenityRest.rest()
                .given().log().all()
                .header("Content-Type","application/xml")
                .body(body)
                .when()
                .post()
                .then();

    }
}
