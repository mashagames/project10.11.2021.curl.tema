package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.*;
import java.util.Map.Entry;


@RestController
public class ApiController {
    private List<String> topics = new ArrayList<>();
    private List<HashMap<String, String>> topicComments = new ArrayList<>();

    // 1.      Создать тему
    /* curl -s -X POST http://localhost:8080/topics -H 'Content-Type: text/plain' -d 'Topic 1' */
    @PostMapping("topics")
    public void createTopic(@RequestBody String text) {
        topics.add(text);
        topicComments.add(new HashMap<String, String>());
    }

    // 2.      Удалить тему
    /* curl -s -X DELETE http://localhost:8080/topics/2 */
    @DeleteMapping("topics/{index}")
    public void deleteText(@PathVariable("index") Integer index) {
        topics.remove((int) index);
        topicComments.remove((int) index);
    }

    // 3.      Выдать список тем
    /*  curl -s -X GET http://localhost:8080/topics/  */
    @GetMapping("topics")
    public List<String> getTopics() {
        return topics;
    }

    // 4.      Обновить тему
    /* curl -s -X PUT http://localhost:8080/topics/0 -H 'Content-Type: text/plain' -d 'Topic New' */
    @PutMapping("topics/{index}")
    public void updateTopic(
            @PathVariable("index") Integer i,
            @RequestBody String topic) {
        topics.remove((int) i);
        topics.add(i, topic);
    }

    // 5.      Выдать количество тем
    /*  curl -s -X GET http://localhost:8080/topicsCount  */
    @GetMapping("topicsCount")
    public String getTopicCount() {
        return String.valueOf(topics.size());
    }

    // 6.      Удалить все темы
    /* curl -s -X DELETE http://localhost:8080/topics */
    @DeleteMapping("topics")
    public void deleteTopics() {
        topics.clear();
        topicComments.clear();
    }

    // 7.      Выдать определенную тему
    /*  curl -s -X GET http://localhost:8080/topics/0  */
    @GetMapping("topics/{index}")
    public String getTopic(@PathVariable("index") Integer index) {
        return topics.get(index);
    }

    // 8.      Добавить комментарий пользователя (username) в определенной теме
    /* curl -s -X POST -d 'user=user1&comment=comment1' http://localhost:8080/topics/0/comments  */
    @PostMapping("topics/{index}/comments")
    public void commentTopic(
            @PathVariable("index") Integer index,
            @RequestParam("user") String user, @RequestParam("comment") String comment) {
        HashMap<String, String> topicComment = topicComments.get(index);
        topicComment.put(user, comment);
    }

    // 9.      Удалить комментарий из определенной темы
    /* curl -s DELETE -d 'user=user1&comment=comment1' http://localhost:8080/topics/0/comments */
    @DeleteMapping("topics/{index}/comments")
    public void delCommentTopic(
            @PathVariable("index") Integer index,
            @RequestParam("user") String user, @RequestParam("comment") String comment) {
        HashMap<String, String> topicComment = topicComments.get(index);
        topicComment.remove(user, comment);
    }

    // 10.   Обновить комментарий в определенной теме
    /* curl -s -X PUT -d 'comment=comment1&comment_new=commentNew' http://localhost:8080/topics/0/commUpd  */
    @PutMapping("topics/{index}/commUpd")
    public void updateComment(
            @PathVariable("index") Integer index,
            @RequestParam("comment") String comment,
            @RequestParam("comment_new") String comment_new) {
        HashMap<String, String> topicComment = topicComments.get(index);
        for (Map.Entry entry : topicComment.entrySet()) {
             if (entry.getValue() == comment) {
                String user = (String) entry.getKey();
                topicComment.remove(user, comment);
                topicComment.put(user, comment_new);
            }
        }
    }

    // 11.   Выдать список комментариев определенной темы
    /* curl -s -X GET http://localhost:8080/topics/0/comments  */
    @GetMapping("topics/{index}/comments")
    public List<String> getCommentTopic(
            @PathVariable("index") Integer index) {
        HashMap<String, String> topicComment = topicComments.get(index);
        List<String> commentsList = new ArrayList<>();
        for (Map.Entry entry : topicComment.entrySet()) {
            commentsList.add(""+entry.getKey()+";"+entry.getValue());
        }
        return commentsList;
    }

    // 12.   Комментарии должны быть привязаны к пользователям (username)
    // 13.   Выдать список комментариев определенного пользователя
    /* curl -s -X GET http://localhost:8080/topics/users/user1 */
    @GetMapping("topics/users/{user}")
    public List<String> getCommentsUser(@PathVariable("user") String user) {
        List<String> commentsList = new ArrayList<>();
        for (HashMap<String, String> topicComment : topicComments) commentsList.add(topicComment.get(user));
        return commentsList;
    }

    // 14.   Обновить комментарий определенного пользователя в определенной теме
    /* curl -s -X PUT -d 'comment_new=commentNew' http://localhost:8080/topics/0/comments/comment1/users/user1  */
    @PutMapping("topics/{index}/comments/{comment}/users/{user}")
    public void updateUserComment(
            @PathVariable("index") Integer index, @PathVariable("comment") String comment, @PathVariable("user") String user,
            @RequestParam("comment_new") String comment_new) {
        HashMap<String, String> topicComment = topicComments.get(index);
        topicComment.remove(user, comment);
        topicComment.put(user, comment_new);
    }

    // 15.   Удалить все комментарии определенного пользователя
    /* curl -s DELETE  http://localhost:8080/topics/users/user1 */
    @DeleteMapping("topics/users/{user}")
    public void delCommentUser(
            @PathVariable("user") String user) {
        for (HashMap<String, String> topicComment : topicComments) topicComment.remove(user);
    }

}
