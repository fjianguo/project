package com.jianguo.dao;

import com.jianguo.domain.Terms;
import com.jianguo.domain.Weibo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WeiboDao {

    /**
     * 获取微博信息列表
     *
     * @return
     */
    List<Weibo> findAllSearch();

    /**
     * 获取微博信息列表
     *
     * @return
     */
    List<Terms> findAll();

    /**
     * 获取微博信息列表并排序
     *
     * @return
     */
    List<Weibo> findRankSearch();

    /**
     * 根据微博 title,查询微博信息
     *
     * @param title
     * @return
     */
    List<Weibo> findByTitle(@Param("title") String title);

    /**
     * 根据微博 title,查询微博信息
     *
     * @param title
     * @return
     */
    List<Weibo> findWeiboByTitle(@Param("title") String title);

    Long saveWeibo(Weibo weibo);

    Long updateWeibo(Weibo weibo);

    Long deleteWeibo(Long id);
}
