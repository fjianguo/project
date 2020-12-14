#!/usr/bin/python3

import jieba
import pymysql

def func1(my_list):
    while ' ' in my_list:
        my_list.remove(' ')
    return list(set(my_list))

def sql():

    sqlSort = "select * from (select rank() over(order by s_view desc)\
    as s_rank ,s_view,s_title from ( select sum(s_view) \
    as s_view ,s_title as  s_title from search group by s_title ) as t1 ) as t2;"

    sqlSum = "select * from (select sum(s_view) as s_view from (select * from search where s_title like %s) as t1)as t2;"
    sqlInsert = "insert into terms (term,number) values (%s,%s)"

    # 打开数据库连接
    db = pymysql.connect("localhost","root","156176pwd","weibo" )

    # 使用 cursor() 方法创建一个游标对象 cursor
    cursor = db.cursor()

    # 使用 execute()  方法执行 SQL 查询 
    cursor.execute(sqlSort)

    test = cursor.fetchall()

    list = []

    for terms in test:
        seg_list = jieba.lcut(terms[2], cut_all=False)
        list = list + seg_list
    for term in func1(list):
        newTerm = "%" + term + "%"
        cursor.execute(sqlSum,newTerm)
        view = cursor.fetchall()
        number = int(view[0][0])
        cursor.execute(sqlInsert,(term,number))
        db.commit()

    db.close()

if __name__ == '__main__':
    sql()