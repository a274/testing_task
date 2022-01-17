package ru.a274.reportdirapp.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;
import ru.a274.reportdirapp.model.db.Status;
import ru.a274.reportdirapp.model.request.RequestGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class GroupValidityService {
    private final String BASE_URL = "http://localhost:8081/groups";
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private List<String> emailList = new ArrayList<>();
    private String message = "";

    public String isGroupValid(String groupId) {
        message = "";
        String answer = sendRequest(groupId);
        if (!message.isEmpty()) return message;
        List<RequestGroup> groups = parseAnswer(answer);
        if (!message.isEmpty()) return message;
        allGroupsActive(groups);
        if (!message.isEmpty()) return message;
        formEmailList(groups);
        return message;
    }

    private String sendRequest(String groupId) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(BASE_URL)).newBuilder();
        urlBuilder.addQueryParameter("id", groupId);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            return Objects.requireNonNull(response.body()).string();

        } catch (IOException e) {
            message = "Request is not successful";
            return message;
        }
    }

    private List<RequestGroup> parseAnswer(String answer) {
        List<RequestGroup> responseResult = new ArrayList<>();
        try {
            responseResult = Arrays.asList(gson.fromJson(answer,RequestGroup[].class));
            return responseResult;

        } catch (JsonSyntaxException e) {
            //group in not found
            log.error(answer);
            this.message = answer;
        }
        return responseResult;
    }

    public List<String> getEmailList() {
        return emailList;
    }

    public String getMessage() {
        return message;
    }

    private boolean isGroupActive(RequestGroup group) {
        if (!group.getStatus().equals(Status.ACTIVE.name())) {
            message = "Group is not active for id: " + group.getId();
            log.info(message);
            return false;
        }
        return true;
    }

    private void allGroupsActive(List<RequestGroup> groupList) {
        for (RequestGroup group : groupList) {
            if (!isGroupActive(group)) {
                return;
            }
        }
    }

    private void formEmailList(List<RequestGroup> groups) {
        List<String> emailList = new ArrayList<>();
        for (RequestGroup group : groups) {
            emailList.addAll(group.getEmail());
            log.info(String.valueOf(group.getEmail()));
        }
        this.emailList = emailList;
    }
}
