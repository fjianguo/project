package com.jianguo.service.impl;

import com.jianguo.dao.WeiboDao;
import com.jianguo.domain.Terms;
import com.jianguo.domain.Weibo;
import com.jianguo.service.WeiboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Service
public class WeiboServiceImpl implements WeiboService {

    @Autowired
    private WeiboDao weiboDao;

    public List<Weibo> findAllSearch(){return weiboDao.findAllSearch();}

    public List<Terms> findAll(){return weiboDao.findAll();}

    public List<Weibo> findRankSearch(){return weiboDao.findRankSearch();}

    public List<Weibo> findByTitle(String title) {
        return weiboDao.findByTitle(title);
    }

    public List<Weibo> findWeiboByTitle(String title) {
        return weiboDao.findWeiboByTitle(title);
    }

    @Override
    public Long saveWeibo(Weibo weibo) {
        return weiboDao.saveWeibo(weibo);
    }

    @Override
    public Long updateWeibo(Weibo weibo) {
        return weiboDao.updateWeibo(weibo);
    }

    @Override
    public Long deleteWeibo(Long id) {
        return weiboDao.deleteWeibo(id);
    }
}
