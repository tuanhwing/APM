package graduating.project.com.apm.socket;

import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import graduating.project.com.apm.utils.Utils;

/**
 * Created by Tuan on 20/09/2017.
 */

public class SocketSingleton {

    private static Socket socket;
    {
        try {
            socket  = IO.socket(Utils.IP_SERVER + ":" + Utils.PORT_SERVER);
        } catch(URISyntaxException e){
            Log.d(Utils.TAG_SOCKET,String.valueOf(e.getMessage()));
        }
    }

    private static SocketSingleton instance = null;

    private SocketSingleton()
    {
    }

    public static SocketSingleton getInstance(){
        if(instance == null) {
            instance = new SocketSingleton();
        }
        return instance;
    }

    public Socket getSocket() {
        return this.socket;
    }

}
