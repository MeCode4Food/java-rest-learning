package com.example.chick;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalController {
    private Gson gson = new Gson();

    private final CrudRepository<Journal, Integer> journalRepository;

    @Autowired
    public JournalController(CrudRepository<Journal, Integer> journalRepository) {
        this.journalRepository = journalRepository;
    }

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

        @GetMapping(value = "/all", produces = "application/json")
        public String getAllJournals(){

            Iterable<Journal> iterableJournalResults = journalRepository.findAll();
            JsonArray jsonArrayJournalResults = gson.toJsonTree(iterableJournalResults).getAsJsonArray();

            JsonObject baseObject = gson.fromJson("{}", JsonObject.class);
            baseObject.addProperty("numFound", jsonArrayJournalResults.size());
            baseObject.add("results", jsonArrayJournalResults);
            return gson.toJson(baseObject);
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



