package nz.webshop.Controllers;

import nz.webshop.models.Category.CategoryMini;
import nz.webshop.models.Product.ProductMini;
import nz.webshop.models.ProductCategory.ProductCategory;
import nz.webshop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class ProductResource {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductMiniRepository productMiniRepository;
    @Autowired
    CategoryMiniRepository categoryMiniRepository;
    @Autowired
    ProductCategoryRepository productCategoryRepository;


    @GetMapping(value = "/product")
    public List<ProductMini> getAll() {
        return productMiniRepository.findAll();
    }


    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductMini> getProduct(@PathVariable("id") Integer id) {
        ProductMini product = productMiniRepository.findOne(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    @GetMapping(value = "/productbycategory")
    public List<ProductMini> getProductCategoriesAll() {
        return productMiniRepository.findAll();
    }
  /*  @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/productbycategory/undefined")
    public List<ProductMini> getProductCategoriesAll1() {
        return productMiniRepository.findAll();
    }*/

    //  public List<Product> getAll() {        return productRepository.findAll();    }


    /*@RequestMapping(value = "", method = RequestMethod.GET)
    public List<Object> getProducts() {
        List<Object> products = productRepository.findProducts();
        System.out.println("special order");
        return products;
    }*/



   /* public  ResponseEntity<Product> getProduct(@PathVariable("id") Integer id) {
        Product product = productRepository.findOne(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }*/


    @RequestMapping(value = "/productbycategory/{id}", method = RequestMethod.GET)

    public ResponseEntity<List<ProductMini>> getProductsByCategoryID(@PathVariable("id") Integer id) {
        CategoryMini categoryById = categoryMiniRepository.getOne(id);
        System.out.println("category by id name " + categoryById.getName());
        List<ProductCategory> productCategories = productCategoryRepository.findByCategoryId(id);

        List<ProductMini> products = new ArrayList<>();
        Integer productId;
        for (ProductCategory pc : productCategories) {
            productId = pc.getProductId();
            products.add(productMiniRepository.findOne(productId));
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


}
