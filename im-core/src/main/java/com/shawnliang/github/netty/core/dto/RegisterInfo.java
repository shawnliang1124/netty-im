package com.shawnliang.github.netty.core.dto;

import java.util.Map;
import lombok.Data;

/**
 * Description :   客户端信息.
 *
 * @author : Phoebe
 * @date : Created in 2021/12/19
 */
@Data
public class RegisterInfo {

    /**
     * 客户端信息
     */
    private Map<String, UserDataInfo> clientMap;

}
