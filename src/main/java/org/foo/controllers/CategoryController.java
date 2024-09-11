package org.foo.controllers;


import org.foo.dto.CategoryDto;
import org.foo.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/list")
  public List<CategoryDto> getAllCategories(){
    return categoryService.getAllCategories();
  }

  @PostMapping
  public ResponseEntity<String> createCategory(@RequestBody CategoryDto categoryDto){
    categoryService.save(categoryDto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PutMapping("/edit/{id}")
  public ResponseEntity<String> editCategory(@PathVariable("id")Long id , @RequestBody CategoryDto categoryDto){
    categoryService.update(id,categoryDto);
    return ResponseEntity.status(HttpStatus.OK).build();

  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteCategory(@PathVariable("id")Long id){
    categoryService.deleteById(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }




}
