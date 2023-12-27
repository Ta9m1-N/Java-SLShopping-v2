package com.slshop.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.slshop.cart.CartService;
import com.slshop.common.entity.product.Product;
import com.slshop.security.CustomerUserDetails;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    
    private final CartService cartService;

    @Autowired
    public ProductController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping
    public String viewProducts(Model model) {
        List<Product> products = productService.getProducts();
        model.addAttribute("products", products);
        return "products/products";
    }

    @GetMapping("/detail/{productId}")
    public String viewProductDetail(@PathVariable("productId") Long productId, Model model, @AuthenticationPrincipal CustomerUserDetails user) {
        Product product = productService.getProduct(productId);
        int cartItemValue = this.cartService.getQuantityByProductIdAndCustomerId(productId, user);
        model.addAttribute("id", productId);
        model.addAttribute("product", product);
        model.addAttribute("cartItemValue", cartItemValue);
        return "products/product_detail";
    }
}
