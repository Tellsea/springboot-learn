package cn.tellsea.mapper;

import cn.tellsea.entity.UserInfo;
import cn.tellsea.vo.UserInfoVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author Tellsea
 * @Description Created on 2019/8/4
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 根据用户名查询
     *
     * @param userName
     * @return
     */
    List<UserInfoVo> findUserInfoByUserName(String userName);
}
