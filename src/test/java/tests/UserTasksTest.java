package tests;

import api.ApiClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import model.Todo;
import model.User;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTasksTest {

    private boolean isUserInFanCodeCity(User user) {
        double lat = Double.parseDouble(user.getAddress().getGeo().getLat());
        double lng = Double.parseDouble(user.getAddress().getGeo().getLng());
        return lat >= -40 && lat <= 5 && lng >= 5 && lng <= 100;
    }

    private double getCompletedTaskPercentage(List<Todo> todos) {
        long totalTasks = todos.size();
        long completedTasks = todos.stream().filter(Todo::isCompleted).count();
        return ((double) completedTasks / totalTasks) * 100;
    }

    @Test
    public void testFanCodeUsersCompletedTasks() throws IOException {
        Response usersResponse = ApiClient.getUsers();
        Response todosResponse = ApiClient.getTodos();

        ObjectMapper mapper = new ObjectMapper();
        List<User> users = mapper.readValue(usersResponse.asString(), new TypeReference<List<User>>() {});
        List<Todo> todos = mapper.readValue(todosResponse.asString(), new TypeReference<List<Todo>>() {});

        List<User> fanCodeUsers = users.stream()
                .filter(this::isUserInFanCodeCity)
                .collect(Collectors.toList());

        for (User user : fanCodeUsers) {
            List<Todo> userTodos = todos.stream()
                    .filter(todo -> todo.getUserId() == user.getId())
                    .collect(Collectors.toList());

            double completedPercentage = getCompletedTaskPercentage(userTodos);
            Assert.assertTrue("User " + user.getId() + " has less than 50% completed tasks", completedPercentage > 50);
        }
    }
}
