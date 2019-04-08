package app.apis;

import app.dtos.SneakerDTO;
import app.services.SneakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sneaker")
public class SneakerController {

    @Autowired(required = false)
    private SneakerService sneakerService;

    /**
     * CAUTION:
     *      If an existing customer is to be updated but some attributes are missing (i.e. retail price, description, etc),
     *      those will be overwritten as their default values or null. To update these, you must create separate methods.
     * @param sneakerDTO must have at least "productCode" and "productName" non-null attributes
     */
    @PostMapping("/save")
    public void saveSneaker(@Valid @RequestBody SneakerDTO sneakerDTO) {
        sneakerService.saveSneaker(sneakerDTO);
    }

    @GetMapping("/all")
    public List<SneakerDTO> getAllSneakers() {
        return sneakerService.getAllSneakers();
    }
}
