package app.services;

import app.dtos.CustomerDTO;
import app.entities.Customer;
import app.interfaces.CustomerRepository;
import app.interfaces.CustomerServiceInterface;
import app.mappers.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService implements CustomerServiceInterface {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper mapper;

    public void saveCustomer(CustomerDTO dto) {
        //parameter should be DTO and must be converted to Customer Entity before save

        customerRepository.save(mapper.toFullEntity(dto));
    }

    public List<CustomerDTO> getAllCustomers() {

        //convert each record into a DTO then return as list?
        //map the customer list to convert each Customer to CustomerDTO then remake them as list again
        return convertToDTOList(customerRepository.findAll());
    }

    public Boolean getSubscribedStatus(long customerId) {
        return customerRepository.findById(customerId).get().getSubscribed();
    }

    /**
     *
     * cannot map dto to entity and save because of missing attributes
     *
     * @param customerDTO only has "customerId" and "subscribed" properties
     */

    public void setSubscribedStatus(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerDTO.getCustomerId()).get();
        customer.setSubscribed(customerDTO.getSubscribed());
        customerRepository.save(customer);
    }

    public void deleteCustomer(long customerId) {
        customerRepository.deleteById(customerId);
    }

    public List<String> getSubscribedEmails() {
        return customerRepository.findSubscribedEmails();
    }

    /**
     * findAll(Example.of(customer)) returns 0 empty array. dont know why
     * if really not working, use @Query (though not desired)
     * @return
     */
    public List<CustomerDTO> getSubscribedCustomers() {

        //code that returns empty array. Example.of(customer) not working
        Customer customer = new Customer();
        customer.setSubscribed(true);
        return convertToDTOList(customerRepository.findAll(Example.of(customer)));

        //using custom query from customerRepository (working but not desirable as it does not take advantage of JPA provided query language)
        //return convertToDTOList(customerRepository.findSubscribedCustomers());
    }

    /**
     * not sure if parameter is correct. since i will be testing the Entity's increasePoints(...) method, ignore other factors
     * @param dto contains "customerId" and "amount" properties only
     */
    public void increasePoints(CustomerDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId()).get();
        customer.increasePoints(dto.getAmount());
        customerRepository.save(customer);
    }

    /**
     *
     * FOOD FOR THOUGHT: should I create new property in DTO instead of using "points" to avoid misuse????
     *
     * @param dto contains "customerId" and "points" attributes (points attribute is used to decrease from the total points NOT the new points value)
     */
    public void decreasePoints(CustomerDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId()).get();
        customer.decreasePoints(dto.getPoints());
        customerRepository.save(customer);
    }

    /**
     *
     * @param customerId
     * @return points
     */
    public double getCustomerPoints(long customerId) {
        return customerRepository.findById(customerId).get().getPoints();
    }

    private List<CustomerDTO> convertToDTOList(List<Customer> customerList) {
        return customerList.stream().map(customer -> {
            return mapper.toFullDTO(customer);
        }).collect(Collectors.toList());
    }
}
