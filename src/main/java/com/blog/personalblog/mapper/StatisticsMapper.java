package com.blog.personalblog.mapper;

import com.blog.personalblog.vo.StatisticsTagCountVO;
import com.blog.personalblog.vo.StatisticsTopCountVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: SuperMan
 * @create: 2023-05-20
 **/

@Repository
public interface StatisticsMapper {

    StatisticsTopCountVO getTopCount();

    List<StatisticsTagCountVO> getTagCount();

}
