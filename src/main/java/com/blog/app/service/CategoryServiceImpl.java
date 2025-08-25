package com.blog.app.service;

import com.blog.app.dto.CategoryDto;
import com.blog.app.entity.Category;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = Category.builder().id(categoryDto.id())
                .name(categoryDto.name())
                .description(categoryDto.description())
                .build();
        Category savedCategory = categoryRepository.save(category);
        return new CategoryDto(savedCategory.getId(), savedCategory.getName(), savedCategory.getDescription());
    }


    public CategoryDto getCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(categoryId, "Category", "id"));

        return new CategoryDto(category.getId(), category.getName(), category.getDescription());
    }


    public List<CategoryDto> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map((category) -> new CategoryDto(category.getId(), category.getName(), category.getDescription()))
                .collect(Collectors.toList());
    }


    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(categoryId, "Category", "id"));

        category.setName(categoryDto.name());
        category.setDescription(categoryDto.description());
        category.setId(categoryId);

        Category updatedCategory = categoryRepository.save(category);

        return new CategoryDto(updatedCategory.getId(), updatedCategory.getName(), updatedCategory.getDescription());
    }


    public void deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(categoryId, "Category", "id"));

        categoryRepository.delete(category);
    }
}
