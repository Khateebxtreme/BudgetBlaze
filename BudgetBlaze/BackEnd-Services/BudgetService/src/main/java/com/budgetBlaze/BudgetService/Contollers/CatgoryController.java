package com.budgetblaze.BudgetService.Contollers;


import com.budgetblaze.BudgetService.Exceptions.CategoryAlreadyExistsException;
import com.budgetblaze.BudgetService.Exceptions.CategoryNotFoundException;
import com.budgetblaze.BudgetService.Model.Category;
import com.budgetblaze.BudgetService.Service.IService;
import com.budgetblaze.BudgetService.dto.CreateCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/CategoryService")
public class CatgoryController {

    @Autowired
    private IService categoryService;

    public CatgoryController(IService categoryService) {
        this.categoryService = categoryService;
    }

    // Category Controller

    @PostMapping("/createCategory")
    public ResponseEntity<Map<String,Object>> createNewCategory(@RequestBody CreateCategoryDto createCategoryDto) throws CategoryAlreadyExistsException {
        Map<String,Object> createCategoryResponseMap =new HashMap<>();
        if(createCategoryDto!=null){
            Category category = categoryService.createNewCategory(createCategoryDto);
            if(category!=null){
                createCategoryResponseMap.put("status","true");
                createCategoryResponseMap.put("message","Category is Created Successfully");
                createCategoryResponseMap.put("category",category);
                return new ResponseEntity<>(createCategoryResponseMap, HttpStatus.OK);

            }
            else{
                createCategoryResponseMap.put("status","false");
                createCategoryResponseMap.put("message","Something happened while adding category");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            }
        }
        else{
            createCategoryResponseMap.put("status","false");
            createCategoryResponseMap.put("message","Input is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("/getCategory")
    public ResponseEntity<Map<String,Object>> getNewCategory(@RequestBody CreateCategoryDto createCategoryDto){
        Map<String,Object> createCategoryResponseMap =new HashMap<>();
        if(createCategoryDto!=null){
            List<Category> listCategory = categoryService.getNewCategory(createCategoryDto);
            if(listCategory.size() >0){
                createCategoryResponseMap.put("status","true");
                createCategoryResponseMap.put("message","Categories are fetched Successfully");
                createCategoryResponseMap.put("category",listCategory);
                return new ResponseEntity<>(createCategoryResponseMap,HttpStatus.OK);

            }
            else{
                createCategoryResponseMap.put("status","false");
                createCategoryResponseMap.put("message","Something happened while fetching categories");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            }
        }
        else{
            createCategoryResponseMap.put("status","false");
            createCategoryResponseMap.put("message","Input is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("/updateCategory/{id}")
    public ResponseEntity<Map<String,Object>> createNewCategory(@PathVariable("id") int categoryId,@RequestBody Category category) throws CategoryNotFoundException {
        Map<String,Object> updateCategoryResponseMap =new HashMap<>();
        if(categoryId >0 ){
            category = categoryService.updateNewCategory(categoryId,category);
            if(category!=null){
                updateCategoryResponseMap.put("status","true");
                updateCategoryResponseMap.put("message","Category is Updated Successfully");
                updateCategoryResponseMap.put("category",category);
                return new ResponseEntity<>(updateCategoryResponseMap,HttpStatus.OK);

            }
            else{
                updateCategoryResponseMap.put("status","false");
                updateCategoryResponseMap.put("message","Something happened while updating category");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            }
        }
        else{
            updateCategoryResponseMap.put("status","false");
            updateCategoryResponseMap.put("message","Category id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("/deleteCategory/{id}")
    public ResponseEntity<Map<String,Object>> deleteNewCategory(@PathVariable("id") int categoryId) throws CategoryNotFoundException {
        Map<String,Object> deleteCategoryResponseMap =new HashMap<>();
        if(categoryId >0){
            Boolean isCategoryDeleted = categoryService.deleteNewCategory(categoryId);
            if(isCategoryDeleted == true){
                deleteCategoryResponseMap.put("status","true");
                deleteCategoryResponseMap.put("message","Category is deleted Successfully");
                return new ResponseEntity<>(deleteCategoryResponseMap,HttpStatus.OK);

            }
            else{
                deleteCategoryResponseMap.put("status","false");
                deleteCategoryResponseMap.put("message","Something happened while deleting category");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            }
        }
        else{
            deleteCategoryResponseMap.put("status","false");
            deleteCategoryResponseMap.put("message","Budget Id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
