package com.jianguo.controller;

import com.jianguo.domain.Terms;
import com.jianguo.domain.Weibo;
import com.jianguo.service.WeiboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class WeiboController {

    @Autowired
    private WeiboService weiboService;

    @RequestMapping(value = "/weibo/title", method = RequestMethod.GET)
    public List<Weibo> findOneWeibo(@RequestParam(value = "title",required = false) String title){
        return weiboService.findByTitle(title);
    }
    
    @RequestMapping(value="/weibo", method = RequestMethod.POST)
    public List<Weibo> findAllSearch(){return weiboService.findAllSearch();}

    @RequestMapping(value="/terms", method = RequestMethod.POST)
    public List<Terms> findAll(){return weiboService.findAll();}

    @RequestMapping(value="/rank/weibo", method = RequestMethod.POST)
    public List<Weibo> findRankSearch(){return weiboService.findRankSearch();}

    @RequestMapping(value = "/api/weibo", method = RequestMethod.POST)
    public List<Weibo> findSearch(@RequestParam(value = "title",required = false) String title){
        title = "%" + title + "%";
        return weiboService.findWeiboByTitle(title);
    }

    @RequestMapping(value = "/weibo/insert", method = RequestMethod.POST)
    public void createWeibo(@RequestBody(required=false) Weibo weibo) {
        Date times = new Date();
        SimpleDateFormat df = new SimpleDateFormat("mm");
        weibo.time = df.format(times);
        weiboService.saveWeibo(weibo);
    }

    @RequestMapping(value = "/weibo/update", method = RequestMethod.POST)
    public void modifyWeibo(@RequestBody(required=false) Weibo weibo) {
        Date times = new Date();
        SimpleDateFormat df = new SimpleDateFormat("mm");
        weibo.time = df.format(times);
        weiboService.updateWeibo(weibo);
    }

    @RequestMapping(value = "/weibo/delete", method = RequestMethod.POST)
    public void modifyWeibo(@RequestParam(value = "id",required = false) Long id) {
        weiboService.deleteWeibo(id);
    }
}
