package com.blog.personalblog.controller;

import com.blog.personalblog.annotation.OperationLogSys;
import com.blog.personalblog.annotation.OperationType;
import com.blog.personalblog.bo.CategoryBO;
import com.blog.personalblog.common.PageRequestApi;
import com.blog.personalblog.entity.Category;
import com.blog.personalblog.service.CategoryService;
import com.blog.personalblog.util.JsonResult;
import com.blog.personalblog.config.page.PageRequest;
import com.blog.personalblog.config.page.PageResult;
import com.blog.personalblog.util.PageUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 分类管理接口
 *
 * @author: SuperMan
 * @create: 2021-11-21
 */
@Api(tags = "分类管理")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * 分页查询列表
     * @param pageRequest
     * @return
     */
    @ApiOperation(value = "分类列表")
    @PostMapping("list")
    public JsonResult<Object> listPage(@RequestBody @Valid PageRequestApi<PageRequest> pageRequest) {
        List<Category> categoryList = categoryService.getCategoryPage(pageRequest.getBody());
        PageInfo pageInfo = new PageInfo(categoryList);
        PageResult pageResult = PageUtil.getPageResult(pageRequest.getBody(), pageInfo);
        return JsonResult.success(pageResult);
    }

    /**
     * 添加分类
     * @return
     */
    @ApiOperation(value = "添加分类")
    @PostMapping("/create")
    @OperationLogSys(desc = "添加分类", operationType = OperationType.INSERT)
    public JsonResult<Object> categoryCreate(@RequestBody @Valid Category category) {
        int isStatus = categoryService.saveCategory(category);
        if (isStatus == 0) {
            return JsonResult.error("添加分类失败");
        }
        return JsonResult.success();
    }

    /**
     * 修改分类
     * @return
     */
    @ApiOperation(value = "修改分类")
    @PostMapping("/update")
    @OperationLogSys(desc = "修改分类", operationType = OperationType.UPDATE)
    public JsonResult<Object> categoryUpdate(@RequestBody @Valid Category category) {
        int isStatus = categoryService.updateCategory(category);
        if (isStatus == 0) {
            return JsonResult.error("修改分类失败");
        }
        return JsonResult.success();
    }

    /**
     * 删除
     * @return
     */
    @ApiOperation(value = "删除分类")
    @PostMapping("/delete")
    @OperationLogSys(desc = "删除分类", operationType = OperationType.DELETE)
    public JsonResult<Object> categoryDelete(@RequestParam(value = "id") int id) {
        categoryService.deleteCategory(id);
        return JsonResult.success();
    }

    /**
     * 搜索文章分类
     * @param bo
     * @return
     */
    @ApiOperation(value = "查询分类名称")
    @PostMapping("/getCategory")
    @OperationLogSys(desc = "查询分类名称", operationType = OperationType.SELECT)
    public JsonResult<Object> getCategoryByName(@RequestBody @Valid CategoryBO bo) {
        List<Category> categoriesByName = categoryService.getCategoriesByName(bo);
        return JsonResult.success(categoriesByName);
    }

}
