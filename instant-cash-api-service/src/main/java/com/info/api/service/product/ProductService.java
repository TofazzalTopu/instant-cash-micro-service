package com.info.api.service.product;

import com.info.api.entity.Product;
import com.info.api.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> saveAll(List<Product> products) {
        return productRepository.saveAll(products);
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public Page<Product> searchProducts(String name, Pageable pageable) {
        return productRepository.findByNameContaining(name, pageable);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> saveProductList() {
        long startTime = System.currentTimeMillis();
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 5000; i++) {
            Product product = new Product();
            product.setName("Product " + i);
            product.setDescription("Description " + i);
            product.setCreateDate(new Date());
            products.add(product);
        }
        products = productRepository.saveAll(products);
        System.out.println("Finished all threads " + products.size());
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + TimeUnit.MILLISECONDS.toSeconds(endTime - startTime) +"s");
        return products;
    }

    public String executorServiceExample() {
        logger.info("Execution started..");
        long startTime = System.currentTimeMillis();
        List<Product> productList = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.execute(this::saveProductList);
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads " + productList.size());
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + TimeUnit.MILLISECONDS.toSeconds(endTime - startTime));
        return "Success";
    }

}
