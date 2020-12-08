package com.jianguo.hot;


public class Hot {
    public static void main(String[] args) throws InterruptedException {
        while(true){
            HotDemo hot = new HotDemo();
            hot.HotDemo();
            Thread.sleep(60000);
        }
    }
}
