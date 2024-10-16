package org.foo.services.impl;

import org.foo.dto.CategoryDto;
import org.foo.mapper.CategoryDtoMapper;
import org.foo.models.Category;
import org.foo.models.Company;
import org.foo.repository.CategoryRepository;
import org.foo.repository.CompanyRepository;
import org.foo.services.CategoryService;
import org.foo.services.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;
  private final CompanyRepository companyRepository;
  private final CategoryDtoMapper categoryDtoMapper;
  private final SecurityService securityService;

  public CategoryServiceImpl(CategoryRepository categoryRepository, CompanyRepository companyRepository, CategoryDtoMapper categoryDtoMapper, SecurityService securityService) {
    this.categoryRepository = categoryRepository;
    this.companyRepository = companyRepository;
    this.categoryDtoMapper = categoryDtoMapper;
    this.securityService = securityService;
  }

  @Override
  public List<CategoryDto> getAllCategories() {
    return null;
  }

  @Override
  public List<String> getCurrentCompanyCategoryDescriptions(CategoryDto categoryDto) {
    return null;
  }

  @Override
  public void save(CategoryDto categoryDto) {

  }

  @Override
  public void update(Long categoryId, CategoryDto categoryDto) {

    categoryRepository.findById(categoryId).ifPresent(category -> {
      category.setDescription(categoryDto.description());
      category.setCompany(companyRepository.findById(categoryDto.companyDto().id()).get());
      categoryRepository.save(category);
    });

  }

  @Override
  public CategoryDto findById(Long id) {
    return categoryDtoMapper.apply(categoryRepository.findById(id).get());
  }

  @Override
  public void deleteById(Long id) {
    Optional<Category> category = categoryRepository.findById(id);
    category.orElseThrow().setIsDeleted(true);
    categoryRepository.save(category.get());
  }
}
