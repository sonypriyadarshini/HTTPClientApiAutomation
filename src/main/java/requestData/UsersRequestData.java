package requestData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersRequestData {

    String name;
    String job;

    public UsersRequestData(){}

    public UsersRequestData(String name, String job){
        this.name =name;
        this.job=job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
