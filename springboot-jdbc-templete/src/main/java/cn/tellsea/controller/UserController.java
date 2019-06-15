package cn.tellsea.controller;

import cn.tellsea.bean.User;
import cn.tellsea.service.UserService;
import cn.tellsea.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseResult getUserById(@PathVariable(value = "id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseResult(200, "查询结果为空", null);
        }
        return new ResponseResult(200, "查询成功", user);
    }

    /**
     * 查询用户列表
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseResult getUserList() {
        List<User> list = userService.getUserList();
        if (list == null || list.isEmpty()) {
            return new ResponseResult(200, "查询结果为空", null);
        }
        return new ResponseResult(200, "查询成功", list);
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody User user) {
        int count = userService.add(user);
        if (count == 0) {
            return new ResponseResult(500, "新增失败", null);
        }
        return new ResponseResult(200, "新增成功", null);
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable(value = "id") Long id) {
        int count = userService.delete(id);
        if (count == 0) {
            return new ResponseResult(500, "删除失败", null);
        }
        return new ResponseResult(200, "删除成功", null);
    }

    /**
     * 根据id修改用户信息
     *
     * @param user
     * @return
     */
    @PostMapping("/update")
    public ResponseResult update(@RequestBody User user) {
        int count = userService.update(user);
        if (count == 0) {
            return new ResponseResult(500, "更新失败", null);
        }
        return new ResponseResult(200, "更新成功", null);
    }
}
