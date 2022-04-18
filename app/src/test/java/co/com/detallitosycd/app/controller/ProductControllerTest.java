package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.SpringSecurityWebAuxTestConfig;
import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.model.ProductModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebAuxTestConfig.class
)
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean
    private ProductModel productModel;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }/*

    @Test
    @WithUserDetails("Administrator User")
    void shouldCreateAProductWithRolAdministrator() throws Exception {
        Product product = new Product("1234567890", "Cuadro 35 x 35","CUADROS",40000,
                "Este es un cuadro de tamaño 35 x 35", "SI");
        MultipartFile mockImage = new MockMultipartFile("file","testingImage.jpg",
                "text/plain","testingImage.jpg".getBytes());

        mockMvc.perform(post("/product/save")
                .contentType(MediaType.ALL)
                .content(objectMapper.writeValueAsString(product))
                .param("file", objectMapper.writeValueAsString(mockImage)))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/product/create/action?success"));

    }
*/
}