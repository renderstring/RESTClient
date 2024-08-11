package ru.glebov.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.glebov.rest.model.User;

import java.util.List;

@Component
public class Communication {

    private final RestTemplate restTemplate;

    private final String URL = "http://94.198.50.185:7081/api/users";

    private String cookie;

    @Autowired
    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<User>>() {});

        cookie = responseEntity.getHeaders().get("Set-Cookie").get(0);

        return responseEntity.getBody();
    }

    public String addUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(URL, request, String.class);
        System.out.println("User was added");

        return responseEntity.getBody();
    }

    public String updateUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange(URL, HttpMethod.PUT, request, String.class);
        System.out.println("User was updated");

        return responseEntity.getBody();
    }

    public String deleteUser(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, request, String.class);
        System.out.println("User with id: " + id + " was deleted");

        return responseEntity.getBody();
    }
}
