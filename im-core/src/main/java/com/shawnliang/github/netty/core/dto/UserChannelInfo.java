package com.shawnliang.github.netty.core.dto;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/19
 */
@Data
public class UserChannelInfo {

    private LocalDateTime loginTime;

    private String userName;
}
