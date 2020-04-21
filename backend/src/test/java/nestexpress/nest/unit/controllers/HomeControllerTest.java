//package nestexpress.nest.unitTest;
package nestexpress.nest.unit.controllers;

import nestexpress.nest.controllers.HomeController;
import nestexpress.nest.entity.Product;
import nestexpress.nest.exceptions.ProductNotFoundException;
import nestexpress.nest.services.ProductService;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(value= HomeController.class, secure=false)
@WebAppConfiguration
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService mockProductService;

    @Mock
    private List<Product> mockProductList;

    @Test
    public void testFeaturedItemsNotFound() throws Exception {
        when(mockProductService.getAll()).thenThrow(new ProductNotFoundException("Products not found."));

        mockMvc.perform(get("/home/getFeatured/{listQty}", 10))
                .andExpect(status().isConflict());

    }

    @Test
    public void testFeaturedItemsFound() throws Exception {
        when(mockProductService.getAll()).thenReturn(mockProductList);

        mockMvc.perform(get("/home/getFeatured/{listQty}", 3))
                .andExpect(status().isOk());
    }

    @Test
    public void testEagleGearItemsNotFound() throws Exception {
        when(mockProductService.findByCategory(33)).thenThrow(new ProductNotFoundException("Products not found."));

        mockMvc.perform(get("/home/getEagleGear/{listQty}/{category}", 100, 0))
                .andExpect(status().isConflict());
    }

    @Test
    public void testEagleGearItemsFound() throws Exception {
        when(mockProductService.findByCategory(33)).thenReturn(mockProductList);

        mockMvc.perform(get("/home/getEagleGear/{listQty}/{category}", 3, 33))
                .andExpect(status().isOk());
    }

    @Test
    public void testSpecialItemsNotFound() throws Exception {
        when(mockProductService.getAll()).thenThrow(new ProductNotFoundException("Products not found."));

        mockMvc.perform(get("/home/getSpecials/{listQty}", 100))
                .andExpect(status().isConflict());
    }

    @Test
    public void testSpecialItemsFound() throws Exception {
        when(mockProductService.getAll()).thenReturn(mockProductList);

        mockMvc.perform(get("/home/getSpecials/{listQty}", 3))
                .andExpect(status().isOk());
    }
}

