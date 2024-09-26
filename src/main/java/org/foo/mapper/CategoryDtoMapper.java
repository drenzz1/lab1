package org.foo.mapper;

import org.foo.dto.CategoryDto;
import org.foo.models.Category;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CategoryDtoMapper implements Function<Category, CategoryDto> {

  private final CompanyDtoMapper companyDtoMapper;

  public CategoryDtoMapper(CompanyDtoMapper companyDtoMapper) {
    this.companyDtoMapper = companyDtoMapper;
  }

  @Override
  public CategoryDto apply(Category category) {
    return new CategoryDto(category.getId(), category.getDescription(),category.getHasProduct(),companyDtoMapper.apply(category.getCompany()));
  }
}
