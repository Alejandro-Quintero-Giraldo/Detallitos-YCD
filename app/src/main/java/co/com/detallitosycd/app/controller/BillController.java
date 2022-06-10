package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.*;
import co.com.detallitosycd.app.model.*;
import co.com.detallitosycd.app.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bill")
public class BillController {

    private BillModel billModel;
    private BillProductModel billProductModel;
    private ProductModel productModel;

    @Autowired
    private UserService userService;


    @PostMapping("create")
    public String createBillOrPutProductInBill(@RequestParam("amountPurchased") Integer amountPurchased,
                             @RequestParam("especifications") String especifications,
                             @RequestParam("productId") String productId) throws SQLException {
        billModel = new BillModel();
        if(checkSession() == null) {
            return "redirect:/login";
        }
        Bill existsBill = billModel.findAvailableBill(checkSession().getUsername());
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
        return "redirect:/bill/available?productAdded";
    }

    @GetMapping("available")
    public String getAvailableBill(Model model) throws SQLException {
        billModel = new BillModel();
        BillProductModel billProductModel = new BillProductModel();
        Bill existBill = billModel.findAvailableBill(checkSession() != null
                ? checkSession().getUsername() : null);
        if(existBill != null){
            existBill.setFinalPrice(calculateFinalPrice(existBill.getBillId()));
            model.addAttribute("activeBill", existBill);
            User user = userService.findUserByUserId(checkSession().getUsername());
            model.addAttribute("user", user);
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
            Integer finalPrice = calculateFinalPrice(billId);
            bill.setStateId(stateClose.getStateId());
            bill.setFinalPrice(finalPrice);
            bill.setDateBill(LocalDateTime.now());
            billModel.updateBill(bill);
        }
        return "redirect:/bill/available";
    }



    @PostMapping("deleteProduct")
    public String deleteProductInBill(@RequestParam("billId") String billId,
                                      @RequestParam("productId") String productId) throws SQLException {
        billProductModel = new BillProductModel();
        boolean result = billProductModel.deleteBillProduct(billId, productId);
        if(!result){
            return "?error";
        }
        return "redirect:/bill/available?productDeleted";
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

    private Integer calculateFinalPrice(String billId) throws SQLException {
        List<Integer> subtotalList = new ArrayList<>();
        billProductModel = new BillProductModel();
        billProductModel.findBillProductsByBillId(billId)
                .forEach(billProduct -> subtotalList.add(billProduct.getSubTotal()));
        Integer finalPrice = 0;
        for (Integer subtotal : subtotalList) {
            finalPrice += subtotal;
        }
        return finalPrice;
    }
}
