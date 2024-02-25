package gmail.endermend05.taskmanager.data;

import gmail.endermend05.taskmanager.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            LoggedInUser user = new LoggedInUser(java.util.UUID.randomUUID().toString(), username);
            boolean result = ServerUtils.sendLoginCommand(username,password);
            System.out.println("the result is: "+result);
            return result? new Result.Success<>(user) : new Result.Fail<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}