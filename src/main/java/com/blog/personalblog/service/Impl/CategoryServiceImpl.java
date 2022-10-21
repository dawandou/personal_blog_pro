package com.blog.personalblog.service.Impl;

import com.blog.personalblog.bo.CategoryBO;
import com.blog.personalblog.entity.Category;
import com.blog.personalblog.mapper.CategoryMapper;
import com.blog.personalblog.service.CategoryService;
import com.blog.personalblog.config.page.PageRequest;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 博客分类实现类
 *
 * @author: SuperMan
 * @create: 2021-11-21
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> getCategoryPage(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum,pageSize);
        List<Category> categoryList = categoryMapper.getCategoryPage();
        return categoryList;
    }

    @Override
    public int saveCategory(Category category) {
        return categoryMapper.create(category);
    }

    @Override
    public int updateCategory(Category category) {
        return categoryMapper.update(category);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        categoryMapper.delete(categoryId);
    }

    @Override
    public Category findById(Integer categoryId) {
        Category category = categoryMapper.getById(categoryId);
        if (category == null) {
            return null;
        }
        return category;
    }

    @Override
    public List<Category> getCategoriesByName(CategoryBO bo) {
        List<Category> categoriesByName = categoryMapper.findCategoriesByName(bo);
        return categoriesByName;
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        Category category = categoryMapper.getCategoryByName(categoryName);
        return category;
    }
}
