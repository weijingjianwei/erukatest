package com.meikinfo.erukaprovide.erukaprovide.dao;

import com.meikinfo.erukaprovide.erukaprovide.domain.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PersonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Person record);

    Person selectByPrimaryKey(Integer id);

    List<Person> selectAll();

    int updateByPrimaryKey(Person record);

    int insertList(List<Person> peoples);

    List<Person> selectByparam(@Param("politicid") Long politicid, @Param("posid")Long posid);
}
