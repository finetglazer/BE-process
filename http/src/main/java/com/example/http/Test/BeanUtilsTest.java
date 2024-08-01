package com.example.http.Test;

import java.util.List;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

@Data
class Obj1 {
    private String name;
    private List<Integer> list;

    public Obj1() {
        this.name = "Obj1";
        this.list = List.of(1, 2, 3);
    }

}

@Data
class Obj2 extends Obj1 {
    private Integer age;
    private String address;

    public Obj2() {
        this.age = 20;
        this.address = "Hanoi";
    }

    @Override
    public String toString() {
        return "Obj2{" +
                "name='" + getName() + '\'' +
                ", list=" + getList() +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}

@Data
class Obj3 extends Obj1 {
    private String age;
    private String addres;
    private String redundantProps;

    @Override
    public String toString() {
        return "Obj3{" +
                "name='" + getName() + '\'' +
                ", list=" + getList() +
                ", age=" + age +
                ", address='" + addres + '\'' +
                ", redundantProps='" + redundantProps + '\'' +
                '}';
    }
}

public class BeanUtilsTest {
    public static void main(String[] args) {
        Obj1 obj1 = new Obj1();
        Obj2 obj2 = new Obj2();
        Obj3 obj3 = new Obj3();
        System.out.println(obj2);
        System.out.println(obj3);
//        BeanUtils.copyProperties(obj2, obj3);
//        System.out.println(obj2);
//        System.out.println(obj3);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(obj2, obj3);
        System.out.println(obj2);
        System.out.println(obj3);
    }
}
