package main

import (
	"database/sql"
	"fmt"
	"log"
	"net/http"
	"time"

	_ "github.com/go-sql-driver/mysql"
)

func search(w http.ResponseWriter, r *http.Request) {

	db, err := sql.Open("mysql", "root:156176pwd@/weibo?charset=utf8")
	checkErr(err)

	//查询数据
	rows, err := db.Query("SELECT * FROM search")
	checkErr(err)

	fmt.Fprintf(w, "[")
	i := 0
	for rows.Next() {
		var id string
		var provinID string
		var cityName string
		var description string
		var times string
		var a string
		err = rows.Scan(&id, &provinID, &cityName, &description, &times)
		if i == 0 {
			a = "{\"Id\":" + id + "," + "\"Rank\":" + "\"" + provinID + "\"," + "\"Title\":" + "\"" + cityName + "\"," + "\"View\":" + "\"" + description + "\"" + "}"
			i++
		} else {
			a = ",{\"Id\":" + id + "," + "\"Rank\":" + "\"" + provinID + "\"," + "\"Title\":" + "\"" + cityName + "\"," + "\"View\":" + "\"" + description + "\"" + "}"
		}
		checkErr(err)
		fmt.Fprintf(w, a)
	}
	fmt.Fprintf(w, "]")
}

func delete(w http.ResponseWriter, r *http.Request) {

	id := r.PostFormValue("id")

	db, err := sql.Open("mysql", "root:156176pwd@/weibo?charset=utf8")
	checkErr(err)

	//删除数据
	stmt, err := db.Prepare("delete from search where s_id=?")
	checkErr(err)

	res, err := stmt.Exec(id)
	checkErr(err)

	_, err = res.RowsAffected()
	if err == nil {
		fmt.Println("删除成功")
	}

	db.Close()
}

func insert(w http.ResponseWriter, r *http.Request) {

	id := r.PostFormValue("id")
	rank := r.PostFormValue("rank")
	title := r.PostFormValue("title")
	view := r.PostFormValue("view")
	t := time.Now().Minute()

	db, err := sql.Open("mysql", "root:156176pwd@/weibo?charset=utf8")
	checkErr(err)

	//插入数据
	stmt, err := db.Prepare("INSERT search SET s_rank=?,s_title=?,s_view=?,s_time=?")
	checkErr(err)

	res, err := stmt.Exec(rank, title, view, t)
	checkErr(err)

	_, err = res.LastInsertId()
	if err == nil {
		fmt.Println("插入成功")
	}

	db.Close()
}

func update(w http.ResponseWriter, r *http.Request) {

	id := r.PostFormValue("id")
	rank := r.PostFormValue("rank")
	title := r.PostFormValue("title")
	view := r.PostFormValue("view")

	db, err := sql.Open("mysql", "root:156176pwd@/weibo?charset=utf8")
	checkErr(err)

	//更新数据
	stmt, err := db.Prepare("update search set s_rank=?,s_title=?,s_view=? where s_id=?")
	checkErr(err)

	res, err := stmt.Exec(rank, title, view, id)
	checkErr(err)

	_, err = res.RowsAffected()
	if err == nil {
		fmt.Println("修改成功")
	}

	db.Close()
}

func main() {
	http.HandleFunc("/search", search)       //设置访问的路由
	http.HandleFunc("/insert", insert)       //设置访问的路由
	http.HandleFunc("/delete", delete)       //设置访问的路由
	http.HandleFunc("/update", update)       //设置访问的路由
	err := http.ListenAndServe(":9191", nil) //设置监听的端口
	if err != nil {
		log.Fatal("ListenAndServe: ", err)
	}
}

func checkErr(err error) {
	if err != nil {
		panic(err)
	}
}
