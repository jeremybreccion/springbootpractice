import app.apis.CustomerController;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerController.class)
@AutoConfigureMockMvc
public class CustomerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void add() throws Exception {
        JSONObject json = new JSONObject();
        json.put("firstName", "Jeremy");
        json.put("lastName", "Reccion");
        json.put("email", "jeremybreccion@gmail.com");
        mvc.perform(MockMvcRequestBuilders.post("/customer/save")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().isOk());
    }
}
