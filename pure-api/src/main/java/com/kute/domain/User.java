package com.kute.domain;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.RandomUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * created by kute on 2018/05/13 10:58
 */
public class User implements Serializable {
    private static final long serialVersionUID = -3368977047962726078L;

    private Integer id;

    private String name;

    private Long age;

    private Date createTime = new Date();

    public User() {
    }

    public User(Integer id) {
        this.id = id;
        this.name = Joiner.on("_").join("Random_name", id);
        this.age = RandomUtils.nextLong(1, 100);
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Long getAge() {
        return age;
    }

    public User setAge(Long age) {
        this.age = age;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public User setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("age", age)
                .add("createTime", createTime)
                .toString();
    }
}
