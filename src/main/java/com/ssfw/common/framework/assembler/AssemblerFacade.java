package com.ssfw.common.framework.assembler;


import java.io.Serializable;
import java.util.List;

/**
 * 类型装配门面
 *
 * @author beets
 * @param <E>
 */
public interface AssemblerFacade<E> {

    /**
     * Entity 转换为 VO
     * @param entity Entity
     * @return VO
     */
    Serializable entityToVO(E entity);

    /**
     * Entity 转换为 VO
     * @param entities Iterable
     * @return List
     */
    List<?> entityToVO(Iterable<E> entities);

}
