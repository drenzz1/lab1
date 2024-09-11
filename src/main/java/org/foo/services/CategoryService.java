package org.foo.services;

import org.foo.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
  List<CategoryDto> getAllCategories();
  List<String> getCurrentCompanyCategoryDescriptions(CategoryDto categoryDto);
  void save(CategoryDto categoryDto);
  void update(Long categoryId, CategoryDto categoryDto);
  CategoryDto findById(Long id);

  void deleteById(Long id);
}
