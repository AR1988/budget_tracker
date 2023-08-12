package org.example;

import org.example.etity.Category;
import org.example.etity.EntityEnum;
import org.example.etity.SubCategory;
import org.example.repository.impl.CategoryRepository;
import org.example.repository.impl.SubCategoryRepository;
import org.example.service.CategoryService;
import org.example.service.InitService;
import org.example.service.SubCategoryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        InitService initService = new InitService();
        initService.initDb();

        SubCategoryRepository subCategoryRepository = new SubCategoryRepository(EntityEnum.SUB_CATEGORY);
        SubCategoryService subCategoryService = new SubCategoryService(subCategoryRepository);

        CategoryRepository categoryRepository = new CategoryRepository(EntityEnum.CATEGORY);
        CategoryService categoryService = new CategoryService(categoryRepository, subCategoryService);

        List<SubCategory> subCategories = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            SubCategory subCategory = new SubCategory();
            subCategory.setName("new " + i);
            subCategories.add(subCategoryService.save(subCategory));
        }

        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Category category = new Category();
            category.setName("category " + i);
            categories.add(categoryService.save(category));
        }

        int lastCat = 0;
        int lastaddedIndex = 0;
        int subCatPerCat = subCategories.size() / categories.size();
        for (Category category : categories) {
            for (int i = lastCat; i < subCatPerCat + lastaddedIndex; i++) {
                categoryService.addSubcategory(category, subCategories.get(i));
                lastCat = i + 1;
            }
            lastaddedIndex = lastCat;
        }

        List<Category> all = categoryService.getAll();
        all.forEach(System.out::println);
    }
}