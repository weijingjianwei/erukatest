package com.meikinfo.erukaprovide.erukaprovide.repository;





import com.meikinfo.erukaprovide.erukaprovide.domain.EsPerson;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * <p>
 * 用户持久层
 * </p>
 *
 * @description: 用户持久层
 */
public interface PersonRepository extends ElasticsearchRepository<EsPerson, Integer> {

    /**
     * 根据年龄区间查询
     *
     * @param min 最小值
     * @param max 最大值
     * @return 满足条件的用户列表
     */
    List<EsPerson> findByAgeBetween(Integer min, Integer max);
}
