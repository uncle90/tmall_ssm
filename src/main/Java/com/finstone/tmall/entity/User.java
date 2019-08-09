package com.finstone.tmall.entity;

public class User {
    private Integer id;

    private String name;

    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /*新增处理方法，提供anonymousName属性*/
    public String getAnonymousName(){
        if(null==name){
            return null;
        }
        if(name.length()==1){
            return "*";
        }
        if(name.length()==2){
            return name.substring(0,1)+"*";//[0,1)
        }
        char[] chars = name.toCharArray();
        for(int i=1; i<chars.length-1; i++){
            chars[i] = '*'; //1,2,..,n-2
        }
        return new String(chars);
    }
}