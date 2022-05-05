package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.Bill;
import co.com.detallitosycd.app.entity.BillProduct;
import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.model.BillModel;
import co.com.detallitosycd.app.model.BillProductModel;
import co.com.detallitosycd.app.model.CompanyModel;
import co.com.detallitosycd.app.model.ProductModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bill")
public class BillController {

    private BillModel billModel;
    private BillProductModel billProductModel;
    private ProductModel productModel;


    @PostMapping("create")
    public String createBillOrPutProductInBill(@RequestParam("amountPurchased") Integer amountPurchased,
                             @RequestParam("especifications") String especifications,
                             @RequestParam("productId") String productId) throws SQLException {
        billModel = new BillModel();
        CompanyModel companyModel = new CompanyModel();
        Bill existsBill = billModel.findAvailableBill();
        if(existsBill != null){
            billModel.putProductInAnAvailableBill(existsBill.getBillId(),productId,especifications,amountPurchased);
        } else {
            Bill bill = new Bill(checkSession().getUsername(), companyModel.findCompanyByNit("123").getNIT());
            billModel.createBill(bill, amountPurchased,especifications,productId);
        }
        return "redirect:/bill/available";
    }

    @GetMapping("available")
    public String getAvailableBill(Model model) throws SQLException {
        billModel = new BillModel();
        Bill existBill = billModel.findAvailableBill();
        if(existBill != null){
            model.addAttribute("activeBill", existBill);
            List<BillProduct> billProductList =  billProductModel.findBillProductsByBillId(existBill.getBillId());
            List<Product> productList = getProductList(billProductList);
            model.addAttribute("billProducts", billProductList);
            model.addAttribute("products", productList);
        } else {
            model.addAttribute("activeBill", null);
        }
        return "shoppingCart";
    }

    private List<Product> getProductList(List<BillProduct> billProductList) {
        productModel = new ProductModel();
        List<Product> productList = new ArrayList<>();
        billProductList.forEach(billProduct -> {
            try {
                Product product = productModel.findById(billProduct.getIdProduct());
                productList.add(product);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return productList;
    }

    private UserDetails checkSession(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        }
        return userDetails;
    }
}
