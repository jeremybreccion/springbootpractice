package app.apis;

import app.dtos.CustomerDTO;
import app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired(required = false)
    private CustomerService customerService;

    /**
     * CAUTION:
     *      If an existing customer is to be updated but some attributes are missing (i.e. points, shipping address, etc),
     *  those will be overwritten as their default values or null. To update these, you must create separate methods.
     *
     * @param customerDTO must have AT LEAST non null "firstName", "lastName", "email" attributes as they are required
     */
    @PostMapping("/save")
    public void saveCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        customerService.saveCustomer(customerDTO);
    }

    @GetMapping("/all")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    /**
     *
     * @param customerDTO only uses "customerId" and "subscribed" attributes
     */
    @PostMapping("/setSubscribe")
    public void setSubscribe(@RequestBody CustomerDTO customerDTO) {
        customerService.setSubscribedStatus(customerDTO);
    }

    @DeleteMapping("/delete/{customerId}")
    public void deleteCustomer(@PathVariable long customerId) {
        customerService.deleteCustomer(customerId);
    }


    /**
     * is this really going to be useful? e.g. maybe getting recipients of newsletter
     * @return array of emails
     */
    @GetMapping("/getSubscribedEmails")
    public List<String> getSubscribedEmails() {
        return customerService.getSubscribedEmails();
    }

    @GetMapping("/getSubscribedCustomers")
    public List<CustomerDTO> getSubscribedCustomers () {
        return customerService.getSubscribedCustomers();
    }

    @PutMapping("/increasePoints")
    public void increasePoints(@Valid @RequestBody CustomerDTO customerDTO) {
        customerService.increasePoints(customerDTO);
    }

    @PutMapping("/decreasePoints")
    public void decreasePoints(@Valid @RequestBody CustomerDTO customerDTO) {
        customerService.decreasePoints(customerDTO);
    }

    @GetMapping("/getPoints/{customerId}")
    public double getPoints(@PathVariable long customerId) {
        return customerService.getCustomerPoints(customerId);
    }
}
