package com.hachismart.jpa.mapper;


import com.hachismart.jpa.bean.QuartzConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @BelongsProject: spring-boot-dynamic-quartz
 * @BelongsPackage: com.hachismart.jpa.mapper
 * @Author: smallodd
 * @CreateTime: 2019-04-15 17:03
 * @Description: 调用持久层的接口
 */
public interface QuartzConfigMapper extends JpaRepository<QuartzConfig, Long> {

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update QuartzConfig con set  con.status=:status where con.id=:id")
    int setStatusById(@Param("status") String status, @Param("id") Long id);


    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update QuartzConfig con set  con.cron=:cron where con.id=:id")
    int setScheduleById(@Param("cron") String cron, @Param("id") Long id);
}
