package nz.webshop.Controllers;

import nz.webshop.models.Order.OrderFromJSON;
import nz.webshop.models.Order.OrderMini;
import nz.webshop.models.Order.OrderMiniMax;
import nz.webshop.models.OrderProduct.OrderProductMini;
import nz.webshop.models.Product.ProductMini;
import nz.webshop.models.Product.Products;
import nz.webshop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class OrdersResource {

    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrdersMiniRepository ordersMiniRepository;
    @Autowired
    OrdersProductRepository ordersProductRepository;
    @Autowired
    OrdersProductMiniRepository ordersProductMiniRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMiniRepository productMiniRepository;





 /*   @RequestMapping(value = "/order", method = RequestMethod.POST)
    public Order addOne (@RequestBody Order order) {
      List<OrderProduct> orderProducts=new ArrayList<>();
        List<Products> products= order.getProducts();
        //if list is not null...
       for (Products ps:products){
           OrderProduct orderProduct= new OrderProduct(order.getId(), ps.getProductId(), ps.getQuantity() );
           orderProducts.add(orderProduct);
       }
       ordersProductRepository.save(orderProducts);
        return order;
    }*/

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    //public Order addOne (@RequestBody Object order) {
    public OrderMini addOne(@RequestBody OrderFromJSON orderFromJSON) {
        OrderMini order1 = new OrderMini();
        // OrderFromJSON opm= (OrderFromJSON) order;
        System.out.println("object order: " + orderFromJSON);
        System.out.println(orderFromJSON.getCustomerId());
        order1.setCustomerId(orderFromJSON.getCustomerId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order1.setDateTime(sdf.format(new Date()));


        ordersMiniRepository.save(order1);

        List<Products> products = orderFromJSON.getProducts();

        for (Products pp : products) {
            OrderProductMini opm = new OrderProductMini();
            opm.setOrderid(order1.getId());
            System.out.println("orderprodict orgerId: " + opm.getOrderid());
            opm.setProductid(pp.getProductId());
            System.out.println("orderprodict productId: " + opm.getProductid());
            opm.setQuantity(pp.getQuantity());
            //make reducing products in stock
            ProductMini pm = productMiniRepository.findOne(pp.getProductId());
            pm.setUnitsInStock((pm.getUnitsInStock())-(pp.getQuantity()));
            ordersProductMiniRepository.save(opm);

        }

        //make reducing products in stock
//ordersProductMiniRepository.save(opm);


        //List<OrderProduct> orderProducts=new ArrayList<>();
        // List<OrderProduct> products= order.getOrderProduct();
        //if list is not null...

        /* for (OrderProduct ps:products){
            OrderProduct orderProduct= new OrderProduct(order.getId(), ps.getProductId(), ps.getQuantity() );
            orderProducts.add(orderProduct);
        }*/
        //  ordersProductRepository.save(products);
        return order1;
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public List<OrderMini> getAll() {
        return ordersMiniRepository.findAll();
    }

    @RequestMapping(value = "/orderproduct", method = RequestMethod.GET)
    public List<OrderProductMini> getAll1() {
        return ordersProductMiniRepository.findAll();
    }

    @RequestMapping(value = "/orderall", method = RequestMethod.GET)
    public List<OrderMiniMax> getAll2() {
        List<OrderMini> orderMinis = ordersMiniRepository.findAll();
        //   List<OrderProductMini> orderProductMinis= ordersProductMiniRepository.findAll();

        ArrayList<OrderMiniMax> orderMiniMaxeList = new ArrayList<>();

        for (OrderMini om : orderMinis) {
            Integer index = om.getId();
            List<OrderProductMini> productsList = ordersProductMiniRepository.findByOrderid(index);
            ArrayList<Products> products = new ArrayList<>();

            for (OrderProductMini opm : productsList) {
                products.add(new Products(opm.getProductid(), opm.getQuantity()));
            }

            orderMiniMaxeList.add(new OrderMiniMax(om.getId(), om.getCustomerId(), om.getDateTime(), products));
        }

        return orderMiniMaxeList;
    }


    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public OrderMiniMax getOneOrder(@PathVariable("id") Integer id) {

        OrderMini orderMini = ordersMiniRepository.findOne(id);
        List<OrderProductMini> productsList = ordersProductMiniRepository.findByOrderid(id);
        ArrayList<Products> products = new ArrayList<>();

        for (OrderProductMini opm : productsList) {
            products.add(new Products(opm.getProductid(), opm.getQuantity()));
        }
        OrderMiniMax orderMiniMax = new OrderMiniMax(orderMini.getId(), orderMini.getCustomerId(), orderMini.getDateTime(), products);

        return orderMiniMax;
    }

    @RequestMapping(value = "/order/customer/{id}", method = RequestMethod.GET)
    public List<OrderMiniMax> getOrdersByCustomer(@PathVariable("id") Integer id) {
        List<OrderMini> orderMinis = ordersMiniRepository.findAll();
        ArrayList<OrderMiniMax> orderMiniMaxeList = new ArrayList<>();

        for (OrderMini om : orderMinis) {

            if (om.getCustomerId() == id) {

                Integer index = om.getId();
                List<OrderProductMini> productsList = ordersProductMiniRepository.findByOrderid(index);
                ArrayList<Products> products = new ArrayList<>();

                for (OrderProductMini opm : productsList) {
                    products.add(new Products(opm.getProductid(), opm.getQuantity()));
                }
                orderMiniMaxeList.add(new OrderMiniMax(om.getId(), om.getCustomerId(), om.getDateTime(), products));
            }
        }

        return orderMiniMaxeList;
    }
}
