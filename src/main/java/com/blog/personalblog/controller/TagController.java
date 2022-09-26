package com.blog.personalblog.controller;

import com.blog.personalblog.annotation.OperationLogSys;
import com.blog.personalblog.annotation.OperationType;
import com.blog.personalblog.common.PageRequestApi;
import com.blog.personalblog.config.page.PageRequest;
import com.blog.personalblog.config.page.PageResult;
import com.blog.personalblog.entity.Tag;
import com.blog.personalblog.service.TagService;
import com.blog.personalblog.util.JsonResult;
import com.blog.personalblog.util.PageUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: SuperMan
 * @create: 2021-11-28
 */
@Api(tags = "标签管理")
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    TagService tagService;

    /**
     * 分页查询列表
     * @param pageRequest
     * @return
     */
    @ApiOperation(value = "标签列表")
    @PostMapping("list")
    public JsonResult<Object> listPage(@RequestBody @Valid PageRequestApi<PageRequest> pageRequest) {
        List<Tag> tagList = tagService.getTagPage(pageRequest.getBody());
        PageInfo pageInfo = new PageInfo(tagList);
        PageResult pageResult = PageUtil.getPageResult(pageRequest.getBody(), pageInfo);
        return JsonResult.success(pageResult);
    }

    /**
     * 添加标签
     * @return
     */
    @ApiOperation(value = "添加标签")
    @PostMapping("/create")
    @OperationLogSys(desc = "添加标签", operationType = OperationType.INSERT)
    public JsonResult<Object> tagCreate(@RequestBody @Valid Tag tag) {
        int isStatus = tagService.saveTag(tag);
        if (isStatus == 0) {
            return JsonResult.error("添加公告失败");
        }
        return JsonResult.success();
    }

    /**
     * 批量添加标签,最多添加10个
     * @param tags 以字符串的方式，以英文逗号隔开。例如：Java,C语言,Python
     *
     * @return
     */
    @ApiOperation(value = "批量添加标签")
    @PostMapping("/batchCreate")
    @OperationLogSys(desc = "批量添加标签", operationType = OperationType.INSERT)
    public JsonResult<Object> batchCreate(@RequestBody @Valid Tag tags) {
        try {
            boolean isStatus = tagService.batchAddTag(tags.getTagName());
            if (!isStatus) {
                return JsonResult.error("批量插入失败！");
            }
        }catch (Exception e) {
            return JsonResult.error(e.getMessage());
        }
        return JsonResult.success();
    }

    /**
     * 批量删除标签
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除标签")
    @DeleteMapping("/batchDelete")
    @OperationLogSys(desc = "批量删除标签", operationType = OperationType.DELETE)
    public JsonResult<Object> batchDelete(@RequestBody @Valid String ids) {
        boolean isDelTag = tagService.batchDelTag(ids);
        if (!isDelTag) {
            return JsonResult.error("批量删除标签失败");
        }
        return JsonResult.success();
    }

    /**
     * 修改标签
     * @return
     */
    @ApiOperation(value = "修改标签")
    @PutMapping("/update")
    @OperationLogSys(desc = "修改标签", operationType = OperationType.UPDATE)
    public JsonResult<Object> tagUpdate(@RequestBody @Valid Tag tag) {
        int isStatus = tagService.updateTag(tag);
        if (isStatus == 0) {
            return JsonResult.error("修改标签失败");
        }
        return JsonResult.success();
    }

    /**
     * 删除
     * @return
     */
    @ApiOperation(value = "删除标签")
    @DeleteMapping("/delete/{id}")
    @OperationLogSys(desc = "删除标签", operationType = OperationType.DELETE)
    public JsonResult<Object> tagDelete(@PathVariable(value = "id") int id) {
        tagService.deleteTag(id);
        return JsonResult.success();
    }
}
