package com.meikinfo.erukaprovide.erukaprovide.domain;

import com.meikinfo.erukaprovide.erukaprovide.consts.EsConsts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * <p>
 * es用户实体类
 * </p>
 */
@Document(indexName = EsConsts.INDEX_NAME, type = EsConsts.TYPE_NAME, shards = 1, replicas = 0)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsPerson {
    /**
     * 主键
     */
    @Id
    private Integer id;


    private Integer age;


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
