package com.shawnliang.github.netty.core.util;

import java.util.Collection;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/26
 */
public class CollectionUtil {

    public static <E> boolean isEmpty(Collection<E> collections) {
        return collections == null || collections.size() == 0;
    }
}
