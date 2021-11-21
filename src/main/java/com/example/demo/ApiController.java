package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.*;
import java.util.Map.Entry;



@RestController
public class ApiController {
    Map<String, String> hashmap = new HashMap<String, String>();
    private List<String> topics = new ArrayList<>();
    @GetMapping("topics")
    public List<String> getTopics() {
        return topics;
    }
    /* curl -X POST http://localhost:8080/topics -H 'Content-Type:
   text/plain' -d 'text' */
    @PostMapping("topics")
    public void createTopic(@RequestBody String text) {
       // topics.add(text);
        HashMap.put("topic", text);
    }
    /*  curl -X GET http://localhost:8080/topics/  */
    /*  curl -X GET http://localhost:8080/topics/1  */
    @GetMapping("topics/{index}")
    public String getTopic(@PathVariable("index") Integer index) {
        return topics.get(index);
    }
    /* curl -X DELETE http://localhost:8080/topics/2 */
    @DeleteMapping("topics/{index}")
    public void deleteText(@PathVariable("index") Integer index) {
        topics.remove((int) index);
    }
    /* curl -X PUT http://localhost:8080/topics/2 "002" */
    @PutMapping("topics/{index}")
    public void updateTopic(
            @PathVariable("index") Integer i,
            @RequestBody String topic) {
        topics.remove((int) i);
        topics.add(i, topic);
    }
}
