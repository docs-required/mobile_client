package gmail.endermend05.taskmanager.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import gmail.endermend05.taskmanager.data.LoginDataSource;
import gmail.endermend05.taskmanager.data.LoginRepository;
import gmail.endermend05.taskmanager.data.ServerUtils;
import gmail.endermend05.taskmanager.ui.login.LoginFragment;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<CustomAdapter> customAdapter;
    private final MutableLiveData<List<Integer>> team_ids;

    private static String login = "";

    public HomeViewModel() {
        customAdapter = new MutableLiveData<>();
        team_ids = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(login.isEmpty()){
                   return;
                }
                team_ids.postValue(ServerUtils.sendGetTeamsIdCommand(login));
                customAdapter.postValue(new CustomAdapter(ServerUtils.sendGetTeamsNameCommand(team_ids.getValue())));
            }
        }).start();


    }
    public static void setLogin(String login){
        HomeViewModel.login = login;
    }
    public LiveData<CustomAdapter> getAdapter() {
        return customAdapter;
    }
}