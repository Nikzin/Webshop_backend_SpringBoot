package nz.webshop.Controllers;

import nz.webshop.models.Category.CategoryMini;
import nz.webshop.repositories.CategoryMiniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import nz.webshop.repositories.CategoryRepository;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/category")
public class CategoryResource {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryMiniRepository categoryMiniRepository;

    @GetMapping(value = "")
    public List<CategoryMini> getAll() {  return categoryMiniRepository.findAll();  }


    //public List<Category> getAll() {        return categoryRepository.findAll();    }

    /*@RequestMapping(value = "", method = RequestMethod.GET)
    public List<Object[]> getCategories() {
        List<Object[]> categories = categoryRepository.findCategories();
        System.out.println("special order");
        return categories;
    }*/
   // @CrossOrigin(origins = "http://localhost:3000")
    //@CrossOrigin
    @RequestMapping(value = "{id}", method = RequestMethod.GET)

    public ResponseEntity<CategoryMini> getCategory(@PathVariable("id") Integer id) {
        CategoryMini category = categoryMiniRepository.findOne(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /*public ResponseEntity<Category> getCategory(@PathVariable("id") Integer id) {
        Category category = categoryRepository.findOne(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }*/




}