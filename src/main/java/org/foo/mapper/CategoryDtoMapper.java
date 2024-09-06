package org.foo.mapper;

import org.foo.dto.CategoryDto;
import org.foo.models.Category;

import java.util.function.Function;

public class CategoryDtoMapper implements Function<Category, CategoryDto> {
  @Override
  public CategoryDto apply(Category category) {
    return new CategoryDto(category.getId(), category.getDescription());
  }
}
