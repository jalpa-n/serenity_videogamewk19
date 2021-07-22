package com.videogameapp.videoGameTest;

import com.videogameapp.TestBase.TestBase;
import com.videogameapp.constants.EndPoints;
import com.videogameapp.model.VideoGamePojo;
import com.videogameapp.utils.TestUtils;
import com.videogameapp.videoGameInfo.VideoGameSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasValue;
import static org.junit.Assert.assertThat;

@RunWith(SerenityRunner.class)
public class VideoGameTest extends TestBase {

    static int videoGameId;
    static String name="PS4"+TestUtils.getRandomValue();
    static String releaseDate="2000-10-01";
    static int reviewScore=TestUtils.getRandomNumber();
    static String category="Shooter"+TestUtils.getRandomValue();
    static String rating="Universal"+TestUtils.getRandomValue();


    //Post Request with json
    @Steps
    VideoGameSteps videoGameSteps;
    @Title("This is create a new video game with json")
    @Test
    public void test001() {

        videoGameSteps.createVideoGame(name,releaseDate,reviewScore,category,rating).statusCode(200);

    }


    @Title("Verify if the video game was added to the application")
    @Test
    public void test002() {

        String p1="findAll{it.name=='";
        String p2="'}.get(0)";

        HashMap<String,Object> videoGame=
                videoGameSteps.getAllVideoGame().statusCode(200)
                        .extract()
                        .path(p1+name+p2);
        System.out.println(videoGame);
        assertThat(videoGame,hasValue(name));
        videoGameId= (int) videoGame.get("id");
    }

    //Put Request
    @Title("This will update the video game information and verify information")
    @Test
    public void test003() {

        name=name+"_Update";
        String p1="findAll{it.name=='";
        String p2="'}.get(0)";
        videoGameSteps.updateVideoGame(videoGameId,name,releaseDate,reviewScore,category,rating).statusCode(200);

        HashMap<String,Object> videoGame=
                videoGameSteps.getAllVideoGame().statusCode(200)
                        .extract()
                        .path(p1+name+p2);
        assertThat(videoGame,hasValue(name));
        System.out.println(videoGame);

    }

    //Delete Request
    @Title("This will delete the video game")
    @Test
    public void test004(){

       videoGameSteps.deleteVideoGameWithId(videoGameId)
       .statusCode(200);
       videoGameSteps.getSingleVideoGame(videoGameId)
               .statusCode(500);
    }

    //Post Request with xml
    @Title("This will create new video game with xml format")
    @Test
    public void test007(){

        String body="    <videoGame category=\"Shooter\" rating=\"Universal\">\n" +
                "        <id>21</id>\n" +
                "        <name>Resident Evil 5</name>\n" +
                "        <releaseDate>2006-10-01T00:00:00+01:00</releaseDate>\n" +
                "        <reviewScore>95</reviewScore>\n" +
                "    </videoGame>";

        videoGameSteps.createVideoGameWithXml(body).statusCode(200);

    }


    //Get Request
    @Title("This will get all Video game")
    @Test
    public void test005() {

        videoGameSteps.getAllVideoGame()
                .log().all()
                .statusCode(200);
    }

    //Get single video game data with id
    @Title("This will get single video game")
    @Test
    public void test006() {
      videoGameSteps.getSingleVideoGame(5)
                .log().all()
                .statusCode(200);
    }


}
