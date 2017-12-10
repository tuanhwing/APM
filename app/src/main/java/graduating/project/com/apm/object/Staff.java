package graduating.project.com.apm.object;

import java.io.Serializable;

/**
 * Created by Tuan on 06/12/2017.
 */

public class Staff implements Serializable{

    private int id;
    private String name;

    public Staff() {
    }

    public Staff(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
