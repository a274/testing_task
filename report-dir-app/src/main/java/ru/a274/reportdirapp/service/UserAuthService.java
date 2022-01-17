package ru.a274.reportdirapp.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.a274.reportdirapp.model.request.AuthUser;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
public class UserAuthService implements UserDetailsService {
    private final String BASE_URL = "http://localhost:8082/users";
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private String message = "";

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        message = "";
        String answer = sendRequestByLogin(login);
        return parseAnswer(answer);
    }


    private String sendRequestByLogin(String login) throws UsernameNotFoundException {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(BASE_URL)).newBuilder();
        urlBuilder.addQueryParameter("login", login);
        String url = urlBuilder.build().toString();
        return sendRequest(url);
    }

    private String sendRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);

        try {
            Response response = call.execute();
            return Objects.requireNonNull(response.body()).string();

        } catch (IOException e) {
            log.info("Request is not successful");
            message = "Request is not successful";
        }
        return message;
    }

    private String sendRequestById(String id) throws UsernameNotFoundException {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(BASE_URL + "/id")).newBuilder();
        urlBuilder.addQueryParameter("id", id);
        String url = urlBuilder.build().toString();
        return sendRequest(url);
    }

    public UserDetails loadUserById(String id) {
        message = "";
        String answer = sendRequestById(id);
        return parseAnswer(answer);
    }

    private AuthUser parseAnswer(String answer) {
        try {
            AuthUser[] requestUser = gson.fromJson(answer, AuthUser[].class);
            return requestUser[0];
        } catch (JsonSyntaxException e) {
            //user not found
            log.error("cannot parse" + answer);
            message = answer;
        }
        return null;
    }

    public String getMessage() {
        return message;
    }

}
