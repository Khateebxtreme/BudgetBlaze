package com.budgetblaze.BudgetService.Contollers;



import com.budgetblaze.BudgetService.Exceptions.*;
import com.budgetblaze.BudgetService.Model.Budget;
import com.budgetblaze.BudgetService.Service.IService;
import com.budgetblaze.BudgetService.dto.CreateBudgetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/BudgetService")
public class BudgetController {


    @Autowired
    private IService budetService;

    public BudgetController(IService budetService) {
        this.budetService = budetService;
    }

    // Budget Controllers

    @PostMapping("/createBudget")
    public ResponseEntity<Map<String,Object>> createNewBudget(@RequestBody CreateBudgetDto createBudgetDto) throws BudgetAlreadyExistsException, NoEligibleBudgetAmountException, RequestMonthGreaterThanCurrentMonthException {
        Map<String,Object> createBudgetResponseMap =new HashMap<>();
        if(createBudgetDto!=null){
            Budget budget = budetService.createNewBudget(createBudgetDto);
            if(budget!=null){
                createBudgetResponseMap.put("status","true");
                createBudgetResponseMap.put("message","Budget is Created Successfully");
                createBudgetResponseMap.put("budget",budget);
                return new ResponseEntity<>(createBudgetResponseMap,HttpStatus.OK);

            }
            else{
                createBudgetResponseMap.put("status","false");
                createBudgetResponseMap.put("message","Something happened while adding budget");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            }
        }
        else{
            createBudgetResponseMap.put("status","false");
            createBudgetResponseMap.put("message","Input is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("/getBudget")
    public ResponseEntity<Map<String,Object>> getNewBudget(@RequestBody CreateBudgetDto createBudgetDto){
        Map<String,Object> createBudgetResponseMap =new HashMap<>();
        if(createBudgetDto!=null){
            List<Budget> listBudget = budetService.getNewBudget(createBudgetDto);
            if(listBudget.size() >0){
                createBudgetResponseMap.put("status","true");
                createBudgetResponseMap.put("message","Budgets are fetched Successfully");
                createBudgetResponseMap.put("budget",listBudget);
                return new ResponseEntity<>(createBudgetResponseMap,HttpStatus.OK);

            }
            else{
                createBudgetResponseMap.put("status","false");
                createBudgetResponseMap.put("message","Something happened while fetching budgets");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            }
        }
        else{
            createBudgetResponseMap.put("status","false");
            createBudgetResponseMap.put("message","Input is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }



    @PostMapping("/getMonthBudget/{month}")
    public ResponseEntity<Map<String,Object>> getMonthBudget(@PathVariable("month") String month) throws BudgetNotFoundException,  NoAllocatedBudgetForMonth {
        Map<String,Object> getMonthBudgetResponseMap =new HashMap<>();
        if(month !=null || !month.isEmpty()){
            List<Budget> monthBudgets = budetService.getMonthBudget(month);
            if(monthBudgets!=null){
                getMonthBudgetResponseMap.put("status","true");
                getMonthBudgetResponseMap.put("message","Budget is Updated Successfully");
                getMonthBudgetResponseMap.put("budget",monthBudgets);
                return new ResponseEntity<>(getMonthBudgetResponseMap,HttpStatus.OK);

            }
            else{
                getMonthBudgetResponseMap.put("status","false");
                getMonthBudgetResponseMap.put("message","Something happened while fetching budgets");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            }
        }
        else{
            getMonthBudgetResponseMap.put("status","false");
            getMonthBudgetResponseMap.put("message","Month is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }




    @PostMapping("/updateBudget/{id}")
    public ResponseEntity<Map<String,Object>> updateNewBudget(@PathVariable("id") int budgetId,@RequestBody Budget budget) throws BudgetNotFoundException, NoPreviousMonthBudgetUpdateAllowedException {
        Map<String,Object> updateBudgetResponseMap =new HashMap<>();
        if(budgetId >0 ){
            budget = budetService.updateNewBudget(budgetId, budget);
            if(budget!=null){
                updateBudgetResponseMap.put("status","true");
                updateBudgetResponseMap.put("message","Budget is Updated Successfully");
                updateBudgetResponseMap.put("budget",budget);
                return new ResponseEntity<>(updateBudgetResponseMap,HttpStatus.OK);

            }
            else{
                updateBudgetResponseMap.put("status","false");
                updateBudgetResponseMap.put("message","Something happened while updating budget");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            }
        }
        else{
            updateBudgetResponseMap.put("status","false");
            updateBudgetResponseMap.put("message","Budget id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("/deleteBudget/{id}")
    public ResponseEntity<Map<String,Object>> deleteNewBudget(@PathVariable("id") int budgetId) throws BudgetNotFoundException {
        Map<String,Object> deleteBudgetResponseMap =new HashMap<>();
        if(budgetId >0){
            Boolean isBudgetDeleted = budetService.deleteNewBudget(budgetId);
            if(isBudgetDeleted == true){
                deleteBudgetResponseMap.put("status","true");
                deleteBudgetResponseMap.put("message","Budget is deleted Successfully");
                return new ResponseEntity<>(deleteBudgetResponseMap,HttpStatus.OK);

            }
            else{
                deleteBudgetResponseMap.put("status","false");
                deleteBudgetResponseMap.put("message","Something happened while deleting budget");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            }
        }
        else{
            deleteBudgetResponseMap.put("status","false");
            deleteBudgetResponseMap.put("message","Budget Id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}
