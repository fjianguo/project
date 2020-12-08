package com.jianguo.service;

import com.jianguo.domain.Terms;
import com.jianguo.domain.Weibo;

import java.util.List;

public interface WeiboService {

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
    List<Weibo> findByTitle(String title);

    /**
     * 根据微博 title,查询微博信息
     *
     * @param title
     * @return
     */
    List<Weibo> findWeiboByTitle(String title);

    /**
     * 新增微博信息
     *
     * @param weibo
     * @return
     */
    Long saveWeibo(Weibo weibo);

    /**
     * 修改微博信息
     *
     * @param weibo
     * @return
     */
    Long updateWeibo(Weibo weibo);

    /**
     *根据微博 ID,删除微博信息
     *
     * @param id
     * @return
     */
    Long deleteWeibo(Long id);
}
