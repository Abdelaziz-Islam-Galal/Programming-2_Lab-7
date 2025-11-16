package Database;

import javax.json.*;
import java.util.*;
import UserManagement.*;

public class UserService extends JsonDatabaseManager<User> {
    public UserService(){
        super("user");
    }

    @Override
    public ArrayList<User> recordsFromJson(JsonObject all){
        ArrayList<User> users = new ArrayList<>();
        JsonArray usersArray = all.getJsonArray("users");

        if (usersArray != null) {
            for (JsonValue value : usersArray) {
                users.add(retrieveUser(value));
            }
        }
        return users;
    }

    private User retrieveUser(JsonValue value){
        User user;
        JsonObject userObj = (JsonObject) value;

        if(userObj.getString("role").equals("Student")){
            Student student = new Student(
                    userObj.getString("name"),
                    userObj.getString("id"),
                    userObj.getString("email"),
                    userObj.getString("password")
            );


            if (userObj.containsKey("progress")) {
                JsonObject progressObj = userObj.getJsonObject("progress");
                Map<String, Map<String, Boolean>> progress = new HashMap<>();

                for (String courseId : progressObj.keySet()) {
                    JsonObject lessonProgressObj = progressObj.getJsonObject(courseId);
                    Map<String, Boolean> lessonProgress = new HashMap<>();

                    for (String lessonId : lessonProgressObj.keySet()) {
                        lessonProgress.put(lessonId, lessonProgressObj.getBoolean(lessonId));
                    }

                    progress.put(courseId, lessonProgress);
                }

                student.setProgress(progress);
            }

            user = student;
        } else {
            user = new Instructor(
                    userObj.getString("name"),
                    userObj.getString("id"),
                    userObj.getString("email"),
                    userObj.getString("password")
            );
        }
        return user;
    }

    @Override
    public JsonObject recordsToJson(ArrayList<User> users){
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (User user : users) {
            JsonObjectBuilder userBuilder = Json.createObjectBuilder()
                    .add("id", user.getSearchKey())
                    .add("name", user.getName())
                    .add("email", user.getEmail())
                    .add("password", user.getPasswordHash())
                    .add("role", user.getRole());


            if (user instanceof Student) {
                Student student = (Student) user;
                JsonObjectBuilder progressBuilder = Json.createObjectBuilder();

                Map<String, Map<String, Boolean>> progress = student.getProgress();
                for (Map.Entry<String, Map<String, Boolean>> courseEntry : progress.entrySet()) {
                    JsonObjectBuilder lessonProgressBuilder = Json.createObjectBuilder();

                    for (Map.Entry<String, Boolean> lessonEntry : courseEntry.getValue().entrySet()) {
                        lessonProgressBuilder.add(lessonEntry.getKey(), lessonEntry.getValue());
                    }

                    progressBuilder.add(courseEntry.getKey(), lessonProgressBuilder.build());
                }

                userBuilder.add("progress", progressBuilder.build());
            }

            arrayBuilder.add(userBuilder.build());
        }

        return Json.createObjectBuilder()
                .add("users", arrayBuilder.build())
                .build();
    }
}