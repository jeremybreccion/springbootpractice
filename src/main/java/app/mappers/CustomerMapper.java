package app.mappers;

import app.dtos.CustomerDTO;
import app.entities.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    private ModelMapper mapper = new ModelMapper();

    public Customer toFullEntity(CustomerDTO dto) {
        return mapper.map(dto, Customer.class);
    }

    public CustomerDTO toFullDTO(Customer customer) {
        return mapper.map(customer, CustomerDTO.class);
    }

    /**
     * this is an example of skipping a field (e.g. points) from Entity -> DTO.
     * skip the methods not the value names?
     *
     * this is not desired because the skipped properties are still there but have null values
     *
     */
    public CustomerDTO toBasicDTO(Customer customer) {
        return mapper.typeMap(Customer.class, CustomerDTO.class).addMappings(map -> {
            map.skip(CustomerDTO::setPoints);
            map.skip(CustomerDTO::setEmail);
            map.skip(CustomerDTO::setSubscribed);
            map.skip(CustomerDTO::setShippingAddress);
        }).map(customer);
    }

}
