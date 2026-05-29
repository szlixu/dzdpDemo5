package com.dzdp.service;

import com.dzdp.dto.CategoryDTO;
import com.dzdp.vo.CategoryVO;

import java.util.List;

public interface CategoryService {

    List<CategoryVO> listCategories();

    CategoryVO getCategoryById(Long id);

    Long createCategory(CategoryDTO dto);

    void updateCategory(Long id, CategoryDTO dto);

    void deleteCategory(Long id);
}
