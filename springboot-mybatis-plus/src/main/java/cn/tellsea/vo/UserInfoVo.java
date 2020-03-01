package cn.tellsea.vo;

import cn.tellsea.entity.UserInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Tellsea
 * @date 2020/3/1
 */
@Getter
@Setter
public class UserInfoVo extends UserInfo {

    private String roleName;
}
