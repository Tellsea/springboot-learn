package cn.tellsea.service;

import cn.tellsea.dto.RedisInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author tycoding
 * @date 2019-02-25
 */
public interface RedisService {

    /**
     * 获取Redis基础info列表
     *
     * @return
     */
    List<RedisInfo> getRedisInfo();

    /**
     * 获取Redis内存占用信息
     *
     * @return
     */
    Map<String, Object> getRedisMemory();

    /**
     * 获取Redis key的数量 --dbsize
     *
     * @return
     */
    Map<String, Object> getRedisDbSize();

    /**
     * 通过pattern正则匹配模糊查询Keys列表
     *
     * @param pattern
     * @return
     */
    Set<String> getKeys(String pattern);

    /**
     * 获取key的value
     *
     * @param arg key
     * @return key-value
     */
    String get(String arg);

    /**
     * 添加key-value
     *
     * @param key   key
     * @param value value
     * @return 是否添加成功
     */
    Boolean set(String key, String value);

    /**
     * 删除key
     *
     * @param keys keys数组
     * @return 成功删除key的个数
     */
    Long del(String... keys);

    /**
     * 判断key是否存在
     *
     * @param keys keys数组
     * @return 存在key的个数
     */
    Long exists(String... keys);

    /**
     * 获取key的剩余过期时间
     *
     * @param key key
     * @return 若key不存在返回-2；若key存在但没有设置过期时间返回-1；否则返回该key的剩余过期时间
     */
    Long pttl(String key);

    /**
     * 以毫秒为单位设置key的生成时间
     *
     * @param key  key
     * @param time 毫秒值
     * @return 设置成功的key个数
     */
    Long pexpire(String key, Long time);
}
