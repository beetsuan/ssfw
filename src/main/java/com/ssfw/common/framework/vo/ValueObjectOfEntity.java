package com.ssfw.common.framework.vo;

import java.util.Collection;
import java.util.List;

/**
 * @author a
 * @date 2022/9/15
 * @since 2.7.3
 */
public interface ValueObjectOfEntity<V,E> {

    V of(E user);

    List<V> of(Collection<E> entities);
}
