package cn.tellsea.web;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.tellsea.bean.User;
import cn.tellsea.service.UserService;
import cn.tellsea.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 数据导出导入功能
 * easypoi官方文档：http://easypoi.mydoc.io/
 * 修改自定义样式：https://blog.csdn.net/qq_37598011/article/details/80918565
 */
@Slf4j
@RestController
public class ExcelController {

    @Autowired
    private UserService userService;

    /**
     * 导出Excel
     *
     * @param response
     */
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) {
        List<User> personList = userService.selectAll();
        // 导出操作
        ExcelUtils.exportExcel(personList, "easypoi导出功能", "导出sheet1", User.class, "测试user.xls", response);
    }

    /**
     * 导入Excel
     *
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public String importExcel(@RequestParam("file") MultipartFile file) {
        ImportParams importParams = new ImportParams();
        // 数据处理
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);
        // 需要验证
        importParams.setNeedVerfiy(false);
        try {
            ExcelImportResult<User> result = ExcelImportUtil.importExcelMore(file.getInputStream(), User.class, importParams);
            List<User> userList = result.getList();
            userService.insertList(userList);
            log.info("从Excel导入数据一共 {} 行 ", userList.size());
            return "导入成功";
        } catch (IOException e) {
            log.error("导入失败：{}", e.getMessage());
            return "导入失败";
        } catch (Exception e1) {
            log.error("导入失败：{}", e1.getMessage());
            return "导入失败";
        }
    }
}
