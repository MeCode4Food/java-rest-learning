package com.example.chick;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalController {
        Gson gson = new Gson();

        @Autowired
        private CrudRepository<Journal, Integer> journalRepository;

        @GetMapping()
        public String getJournal(@RequestParam Integer id) {
            JsonObject replyObject = gson.fromJson("{}", JsonObject.class);

            Optional<Journal> journalResult = journalRepository.findById(id);
            if (journalResult.isPresent()) {
                Journal journal = journalResult.get();
                replyObject.addProperty("message", journal.getContent());
            } else {
                replyObject.addProperty("error", 404);
                replyObject.addProperty("message", "sorry bo wor");
            }

            return gson.toJson(replyObject);
        }

        @GetMapping("/all")
        public String getAllJournals(){
            JsonArray journalResults = new JsonArray();
            journalRepository.findAll().forEach(journalResults::add);

            JsonObject baseObject = gson.fromJson("{}", JsonObject.class);
            baseObject.addProperty("numFound", journalResults.size());
            baseObject.addProperty("results", journalResults);
            return gson.toJson(journalResults);
        }

        @PostMapping(consumes = "application/json")
        public String postJournal(@RequestBody String request) {
            JsonObject requestObject = gson.fromJson(request, JsonObject.class);

            String journalTitle = requestObject.get("title").toString();
            String journalContent = requestObject.get("content").toString();
            Journal newJournal = new Journal(journalTitle, journalContent);

            journalRepository.save(newJournal);

            return "saved liao lo";
        }

}



