package com.smsrz.bookstorewebapp.web.controller;


import com.smsrz.bookstorewebapp.Clients.Catalog.CatalogServiceClient;
import com.smsrz.bookstorewebapp.Clients.Catalog.PagedResult;
import com.smsrz.bookstorewebapp.Clients.Catalog.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {

    private final CatalogServiceClient catalogServiceClient;

    public ProductController(CatalogServiceClient catalogServiceClient) {
        this.catalogServiceClient = catalogServiceClient;
    }

    @GetMapping()
     String index() {
        return "redirect:/products";
    }
    @GetMapping("/products")
    String products(@RequestParam(name="page",defaultValue = "1")int pageNo, Model model) {

        model.addAttribute("pageNo", pageNo);
        return "products";
    }
    @GetMapping("/api/products")
    @ResponseBody
    PagedResult<Product> products(@RequestParam(name = "page",defaultValue = "1") int page) {
        return catalogServiceClient.getProducts(page);
    }
}
