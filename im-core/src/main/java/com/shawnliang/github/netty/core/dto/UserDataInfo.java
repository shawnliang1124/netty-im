package com.shawnliang.github.netty.core.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/19
 */
@Data
@Builder
public class UserDataInfo {

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

}
