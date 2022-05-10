package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.Bill;
import co.com.detallitosycd.app.entity.BillProduct;
import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.entity.State;
import co.com.detallitosycd.app.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        Bill existsBill = billModel.findAvailableBill(checkSession() != null
                ? checkSession().getUsername() : null);
        if(existsBill != null){
            boolean result = billModel.putProductInAnAvailableBill(existsBill.getBillId(),productId,especifications,amountPurchased);
            if(!result){
                return "redirect:/bill/available?productExist";
            }
        } else {
            CompanyModel companyModel = new CompanyModel();
            Bill bill = new Bill(checkSession() != null
                    ? checkSession().getUsername() : null,
                    companyModel.findCompanyByNit("123").getNIT());
            billModel.createBill(bill, amountPurchased,especifications,productId);
        }
        return "redirect:/bill/available";
    }

    @GetMapping("available")
    public String getAvailableBill(Model model) throws SQLException {
        billModel = new BillModel();
        BillProductModel billProductModel = new BillProductModel();
        Bill existBill = billModel.findAvailableBill(checkSession() != null
                ? checkSession().getUsername() : null);
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

    @PostMapping("close")
    public String closeBill(@RequestParam("billId") String billId) throws SQLException {
        billModel = new BillModel();
        StateModel stateModel = new StateModel();
        Bill bill = billModel.findBillById(billId);
        State state = stateModel.findStateById(bill.getStateId());
        if(state.getStateName().equals("DISPONIBLE")){
            State stateClose = stateModel.findAllState()
                    .stream().filter(state1 -> state1.getStateName().equals("CERRADO"))
                    .findAny().get();
            billProductModel = new BillProductModel();
            List<Product> productList = getProductList(billProductModel.findBillProductsByBillId(billId));
            List<Integer> subtotalList = new ArrayList<>();
            productList.forEach(product -> subtotalList.add(product.getProductPrice()));
            Integer finalPrice = 0;
            for (Integer subtotal : subtotalList) {
                finalPrice += subtotal;
            }
            bill.setStateId(stateClose.getStateId());
            bill.setFinalPrice(finalPrice);
            billModel.updateBill(bill);
        }
        return "redirect:/bill/available";
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
