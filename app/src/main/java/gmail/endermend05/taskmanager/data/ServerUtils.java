package gmail.endermend05.taskmanager.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServerUtils {
    private static final String W_ADD_INFO = "wrong additional info";
    private static final Connection connection = new Connection("141.105.64.173", 8787);
    public static String sendCommand(String command) {
        String receive = "";
        try{
            connection.openConnection();
            connection.sendData(command);

            receive = connection.readData();
            connection.sendData("disconnect");
            connection.closeConnection();
        }
        catch (Exception e){
            e.printStackTrace();
            return "Error";
        }

        return receive;
    }

    public static String sendCommands(String command) {
        String receive = "";
        try{
            connection.openConnection();
            connection.sendData(command);

            receive = connection.readData();
        }
        catch (Exception e){
            e.printStackTrace();
            return "Error";
        }

        return receive;
    }

    public static boolean sendLoginCommand(String login, String password){
        String response = sendCommand(String.format(Locale.getDefault(),"client_login:{%s;%s}",Math.abs(login.hashCode()), Math.abs(password.hashCode())));
        if(response.equals(W_ADD_INFO)) return sendRegisterCommand(login,password,login," ");
        return Boolean.parseBoolean(response);
    }

    public static boolean sendRegisterCommand(String login, String password, String name, String description){
        return Boolean.parseBoolean(sendCommand(String.format(Locale.getDefault(),"client_register:{%d;%s;%s;%d}",Math.abs(login.hashCode()),name,description,Math.abs(password.hashCode()))));
    }

    public static List<Integer> sendGetTeamsIdCommand(String login){
        return Arrays.stream(sendCommand(String.format(Locale.getDefault(),"client_get_teamsID:{%d}",Math.abs(login.hashCode()))).split(";")).filter(s->!s.isEmpty()).map(Integer::parseInt).collect(Collectors.toList());
    }

    public static List<String> sendGetTeamsNameCommand(List<Integer> list){
        List<String> result =  list.stream().map(id->sendCommands(Integer.toString(id))).collect(Collectors.toList());
        try{
            connection.sendData("disconnect");
            connection.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
