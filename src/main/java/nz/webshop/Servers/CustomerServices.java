package nz.webshop.Servers;

import nz.webshop.models.Customer.Customer;
import nz.webshop.models.Customer.CustomerNoPassword;
import nz.webshop.models.Customer.Password;
import nz.webshop.repositories.CustomerRepositoryNoPassword;
import nz.webshop.repositories.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServices {

    @Autowired
    CustomerRepositoryNoPassword customerRepositoryNoPassword;
    @Autowired
    PasswordRepository passwordRepository;



    public CustomerNoPassword getTheOneNoPassword(Customer customer) {
        Customer theOneWithPassword = new Customer();
        CustomerNoPassword theOneNoPassword;// =new CustomerNoPassword();
        List<CustomerNoPassword> customersList = customerRepositoryNoPassword.findCustomers(customer.getEmail(), customer.getPassword());
        if (customersList.size() == 1) { //todo if >1 return error and say that there multiple customers with this password and email
           theOneNoPassword = customersList.get(0);
        }
        else
        { theOneNoPassword = null;
        }
        return theOneNoPassword;
    }

    public void saveNewCustomer(Customer customer) {
        Password passwordEntity = new Password();
        passwordEntity.setPassword(customer.getPassword());

        ModelMapper modelMapper = new ModelMapper();
// user here is a prepopulated User instance
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        CustomerNoPassword customerNoPassword = customer;

    //    customer.setPassword(passwordEntity);
        passwordEntity.setCustomer(customerNoPassword);

        customerRepositoryNoPassword.save(customerNoPassword);
        passwordRepository.save(passwordEntity);
        //customer.setPassword(passwordEntity);

        System.out.println("here");

    }

    public Customer findOneById(Integer id) {
        CustomerNoPassword cnp = customerRepositoryNoPassword.getOne(id);
        String password = cnp.getPasswordEntity().getPassword();
        Customer customer = (Customer) cnp;
        customer.setPassword(password);
        return  customer;
    }
}
