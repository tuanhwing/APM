package graduating.project.com.apm.exclass;

import android.widget.CompoundButton;

import org.json.JSONException;
import org.json.JSONObject;

import graduating.project.com.apm.object.Assign;
import graduating.project.com.apm.socket.SocketSingleton;

/**
 * Created by Tuan on 13/01/2018.
 */

public class SwitchChangedActive implements  CompoundButton.OnCheckedChangeListener{
    private Assign assign;

    public SwitchChangedActive(){}
    public SwitchChangedActive(Assign assign){
        this.assign = assign;
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (assign.getActive()){
            case 0: {
                if(isChecked){
                    JSONObject json = new JSONObject();
                    try {
                        json.put("taskid",assign.getTask_id());
                        json.put("staffid",assign.getStaff_id());
                        json.put("type",assign.getProcess());
                        json.put("active",1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    SocketSingleton.getInstance().getSocket().emit("update-active-staff",json);
                }
                break;
            }
            case 1: {
                if(!isChecked){
                    JSONObject json = new JSONObject();
                    try {
                        json.put("taskid",assign.getTask_id());
                        json.put("staffid",assign.getStaff_id());
                        json.put("type",assign.getProcess());
                        json.put("active",0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    SocketSingleton.getInstance().getSocket().emit("update-active-staff",json);
                }
                break;
            }
        }
    }
}
