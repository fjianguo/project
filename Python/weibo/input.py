#!/usr/bin/python3
#encoding=utf-8

import pymysql

def func1(my_list):
    while ' ' in my_list:
        my_list.remove(' ')
    return list(set(my_list))

def sql():

    sql = "select * from (select rank() over(order by number desc)\
    as id,term ,number from ( select * from terms ) as t1 ) as t2;"

    # 打开数据库连接
    db = pymysql.connect("localhost","root","156176pwd","weibo" )

    # 使用 cursor() 方法创建一个游标对象 cursor
    cursor = db.cursor()

    # 使用 execute()  方法执行 SQL 查询 
    cursor.execute(sql)

    test = cursor.fetchall()

    file = open(r'name.txt', mode='a', encoding='UTF-8')
    
    i = 0
    for terms in test:
        file.write(str(terms[1])+','+str(terms[2])+'\n')
        
    file.close()
    db.close()

if __name__ == '__main__':
    sql()
