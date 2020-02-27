package com.meikinfo.erukaprovide.erukaprovide.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private Integer id;

    private String name;

    private String gender;

    private Date birthday;

    private String idcard;

    private String wedlock;

    private Integer nationid;

    private String nativeplace;

    private Integer politicid;

    private String email;

    private String phone;

    private String address;

    private Integer departmentid;

    private Integer joblevelid;

    private Integer posid;

    private String engageform;

    private String tiptopdegree;

    private String specialty;

    private String school;

    private Date begindate;

    private String workstate;

    private String workid;

}
