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

    @Field(type = FieldType.Keyword)
    private Integer age;

    @Field(type = FieldType.Keyword)
    private String name;


    @Field(type = FieldType.Keyword)
    private String gender;

    @Field(type = FieldType.Keyword)
    private Date birthday;

    @Field(type = FieldType.Keyword)
    private String idcard;

    @Field(type = FieldType.Keyword)
    private String wedlock;

    @Field(type = FieldType.Keyword)
    private Integer nationid;

    @Field(type = FieldType.Keyword)
    private String nativeplace;

    @Field(type = FieldType.Keyword)
    private Integer politicid;

    @Field(type = FieldType.Keyword)
    private String email;

    @Field(type = FieldType.Keyword)
    private String phone;

    @Field(type = FieldType.Keyword)
    private String address;

    @Field(type = FieldType.Keyword)
    private Integer departmentid;

    @Field(type = FieldType.Keyword)
    private Integer joblevelid;

    @Field(type = FieldType.Keyword)
    private Integer posid;

    @Field(type = FieldType.Keyword)
    private String engageform;

    @Field(type = FieldType.Keyword)
    private String tiptopdegree;

    @Field(type = FieldType.Keyword)
    private String specialty;

    @Field(type = FieldType.Keyword)
    private String school;

    @Field(type = FieldType.Keyword)
    private Date begindate;

    @Field(type = FieldType.Keyword)
    private String workstate;

    @Field(type = FieldType.Keyword)
    private String workid;

}
