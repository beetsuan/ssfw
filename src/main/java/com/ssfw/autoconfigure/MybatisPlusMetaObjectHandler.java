package com.ssfw.autoconfigure;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ssfw.auth.util.LoginUserUtil;
import com.ssfw.common.util.LocalDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Mybatis Plus 元素据填充
 * @author beets
 */
@Component
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    /**
     * 重写一下insertFill方法 在插入时进行时间的自动维护
     * @param metaObject MetaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("新增更新时间-------------------------");
        LocalDateTime time = LocalDateUtil.nowLocalDateTime();
        Long userId = LoginUserUtil.getLoginUserId();
        String nickname = LoginUserUtil.getLoginNickname();

        this.strictInsertFill(metaObject,"creatorId", Long.class, userId);
        this.strictInsertFill(metaObject,"creatorName", String.class, nickname);
        this.strictInsertFill(metaObject,"createTime", LocalDateTime.class, time);


        this.strictInsertFill(metaObject,"updaterId", Long.class, userId);
        this.strictInsertFill(metaObject,"updaterName", String.class, nickname);
        this.strictUpdateFill(metaObject,"updateTime",LocalDateTime.class, time);
    }

    /**
     * 修改操作是进行时间的维护
     * @param metaObject MetaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("修改更新时间-------------------------");

        LocalDateTime time = LocalDateUtil.nowLocalDateTime();
        Long userId = LoginUserUtil.getLoginUserId();
        String nickname = LoginUserUtil.getLoginNickname();

        this.strictInsertFill(metaObject,"updaterId", Long.class, userId);
        this.strictInsertFill(metaObject,"updaterName", String.class, nickname);
        this.strictUpdateFill(metaObject,"updateTime",LocalDateTime.class, time);
    }

}
