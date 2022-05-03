package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.Bill;
import co.com.detallitosycd.app.entity.BillProduct;
import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.model.BillModel;
import co.com.detallitosycd.app.model.BillProductModel;
import co.com.detallitosycd.app.model.ProductModel;
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
    public String createBillOrPutProductInBill(Bill bill, @RequestParam("amountPurchased") Integer amountPurchased,
                             @RequestParam("especifications") String especifications,
                             @RequestParam("productId") String productId, Model model) throws SQLException {
        Bill existsBill = billModel.findAvailableBill();
        if(existsBill != null){
            billModel.putProductInAnAvailableBill(existsBill.getBillId(),productId,especifications,amountPurchased);
        } else {
            billModel.createBill(bill, amountPurchased,especifications,productId);
        }
        return "redirect:/bill/available";
    }

    @GetMapping("available")
    public String getAvailableBill(Model model) throws SQLException {
        Bill existBill = billModel.findAvailableBill();
        if(existBill != null){
            model.addAttribute("activeBill", existBill);
            List<BillProduct> billProductList =  billProductModel.findBillProductsByBillId(existBill.getBillId());
            List<Product> productList = new ArrayList<>();
            billProductList.forEach(billProduct -> {
                try {
                    Product product = productModel.findById(billProduct.getIdProduct());
                    productList.add(product);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            model.addAttribute("billProducts", billProductList);
            model.addAttribute("products", productList);
        } else {
            model.addAttribute("activeBill", null);
        }
        return "shoppingCart";
    }
}
