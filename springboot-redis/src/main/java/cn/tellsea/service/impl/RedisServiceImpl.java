package cn.tellsea.service.impl;

import cn.tellsea.dto.RedisInfo;
import cn.tellsea.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author tycoding
 * @date 2019-02-25
 */
@Service
public class RedisServiceImpl implements RedisService {

    private static String redisCode = "utf-8";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private RedisConnection execute() {
        return (RedisConnection) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection;
            }
        });
    }

    @Override
    public List<RedisInfo> getRedisInfo() {
        try {
            List<RedisInfo> list = new ArrayList<>();
            Properties info = execute().info();
            for (String key : info.stringPropertyNames()) {
                RedisInfo redisInfo = new RedisInfo();
                redisInfo.setKey(key);
                redisInfo.setValue(info.getProperty(key));
                list.add(redisInfo);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Map<String, Object> getRedisMemory() {
        return getData("memory", execute().info("memory").get("used_memory"));
    }

    @Override
    public Map<String, Object> getRedisDbSize() {
        return getData("dbsize", execute().dbSize());
    }

    @Override
    public Set<String> getKeys(String pattern) {
        try {
            return redisTemplate.keys(pattern);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    @Override
    public String get(String key) {
        try {
            byte[] bytes = execute().get(key.getBytes());
            if (bytes != null) {
                return new String(bytes, redisCode);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean set(String key, String value) {
        return execute().set(key.getBytes(), value.getBytes());
    }

    @Override
    public Long del(String... keys) {
        long result = 0;
        for (int i = 0; i<keys.length;i++) {
            result += execute().del(keys[i].getBytes());
        }
        return result;
    }

    @Override
    public Long exists(String... keys) {
        long result = 0;
        for (int i =0; i<keys.length; i++) {
            if (execute().exists(keys[i].getBytes())) {
                result ++;
            }
        }
        return result;
    }

    @Override
    public Long pttl(String key) {
        return execute().pTtl(key.getBytes());
    }

    @Override
    public Long pexpire(String key, Long time) {
        if (execute().pExpire(key.getBytes(), time)) {
            return 1L;
        }
        return 0L;
    }

    private Map<String, Object> getData(String name, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("create_time", (new Date()).getTime());
        map.put(name, data);
        return map;
    }
}
