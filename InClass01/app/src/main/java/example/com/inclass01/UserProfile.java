package example.com.inclass01;

import java.io.Serializable;

/**
 * Created by Nitin on 8/27/2017.
 */

public class UserProfile implements Serializable{

    String name,address;
    double weight, age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }
}
