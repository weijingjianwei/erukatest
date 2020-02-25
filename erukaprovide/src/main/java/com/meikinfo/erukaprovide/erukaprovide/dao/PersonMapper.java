package com.meikinfo.erukaprovide.erukaprovide.dao;

import com.meikinfo.erukaprovide.erukaprovide.domain.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PersonMapper {
    int insert(Person record);

    int insertList(List<Person> list);

    List<Person> selectAll();
}
