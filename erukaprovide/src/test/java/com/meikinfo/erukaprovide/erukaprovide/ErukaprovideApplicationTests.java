package com.meikinfo.erukaprovide.erukaprovide;

import cn.hutool.core.date.DateUtil;
import com.meikinfo.erukaprovide.erukaprovide.domain.EsPerson;
import com.meikinfo.erukaprovide.erukaprovide.repository.PersonRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@SpringBootTest
class ErukaprovideApplicationTests {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Autowired
    PersonRepository repository;
    @Test
    void contextLoads() {
        esTemplate.deleteIndex(EsPerson.class);
    }

    @Test
    void test1() {
//        Iterable<EsPerson> all = repository.findAll();
//        all.forEach(index->{
//            System.out.println(index);
//        });

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "姚森");
        repository.search(matchQueryBuilder).forEach(item->{
            System.out.println(DateUtil.formatDate(item.getBirthday()));
        });

    }

}
