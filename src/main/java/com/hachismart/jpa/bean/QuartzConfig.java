package com.hachismart.jpa.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @BelongsProject: spring-boot-dynamic-quartz
 * @BelongsPackage: com.hachismart.jpa.bean
 * @Author: smallodd
 * @CreateTime: 2019-04-15 17:00
 * @Description: 数据库实体
 */
@Entity
@Table(name = "quartz_config")
public class QuartzConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "c_name")
    private String name;

    @Column(name = "c_group")
    private String group;

    @Column(name = "c_class_path")
    private String classPath;

    @Column(name = "c_method_name")
    private String methodName;

    @Column(name = "c_status")
    private String status;

    @Column(name = "c_cron")
    private String cron;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}

