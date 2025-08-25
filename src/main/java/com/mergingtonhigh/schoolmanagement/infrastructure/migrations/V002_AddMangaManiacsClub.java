package com.mergingtonhigh.schoolmanagement.infrastructure.migrations;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mergingtonhigh.schoolmanagement.domain.entities.Activity;
import com.mergingtonhigh.schoolmanagement.domain.valueobjects.ActivityType;
import com.mergingtonhigh.schoolmanagement.domain.valueobjects.ScheduleDetails;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;

/**
 * Adds the Manga Maniacs club to the extracurricular activities
 */
@ChangeUnit(id = "add-manga-maniacs-club", order = "002", author = "Copilot")
public class V002_AddMangaManiacsClub {

    private final MongoTemplate mongoTemplate;

    public V002_AddMangaManiacsClub(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void migrate() {
        // Check if Manga Maniacs already exists to avoid duplicates
        Query existingQuery = new Query(Criteria.where("name").is("Manga Maniacs"));
        if (mongoTemplate.count(existingQuery, Activity.class) == 0) {
            // Add Manga Maniacs club with inspiring description
            Activity mangaManiacs = new Activity(
                    "Manga Maniacs",
                    "Embarque numa jornada épica através dos mundos fantásticos dos mangás japoneses! Descubra heróis corajosos, vilões complexos e histórias que vão desde batalhas épicas até romances tocantes. Aqui você vai explorar universos como Naruto, Attack on Titan, One Piece e muito mais, discutindo teorias, criando fan art e mergulhando na rica cultura pop japonesa!",
                    "Terças-feiras, 19:00 - 20:30",
                    new ScheduleDetails(List.of("Tuesday"), LocalTime.of(19, 0), LocalTime.of(20, 30)),
                    15,
                    ActivityType.ARTS);
            mangaManiacs.setParticipants(List.of("sofia@mergington.edu", "leonardo@mergington.edu"));
            mongoTemplate.save(mangaManiacs);
        }
    }

    @RollbackExecution
    public void rollback() {
        // Remove Manga Maniacs club
        mongoTemplate.remove(new Query(Criteria.where("name").is("Manga Maniacs")), Activity.class);
    }
}