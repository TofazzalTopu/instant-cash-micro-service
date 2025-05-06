package com.info.api.controller;

import com.info.api.annotation.GetAPIDocumentation;
import com.info.dto.constants.Constants;
import com.info.api.dto.product.PaginatedResponse;
import com.info.api.dto.product.ProductDTO;
import com.info.api.entity.Product;
import com.info.api.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

//@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.PRODUCTS_API_ENDPOINT)
@Tag(name = "RMS API", description = "APIs for handling remittance operations")
public class ProductController {
    private final ProductService productService;

    private static final int MAX_PAGE_SIZE = 100;


    @GetMapping
    @GetAPIDocumentation
    @Operation(summary = "List of products with pagination and sorting.")
    public ResponseEntity<PaginatedResponse<ProductDTO>> getProducts(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        size = Math.min(size, MAX_PAGE_SIZE);

        Sort.Direction direction = validateSortDirection(sortDir);
        sortBy = validateSortBy(sortBy);

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));
        Page<Product> productPage = productService.getAllProducts(pageable);

        PaginatedResponse<ProductDTO> response = convertToPaginatedResponse(productPage);
        return ResponseEntity.ok(response);
    }

    @GetAPIDocumentation
    @GetMapping("/process")
    @Operation(summary = "Start product operation.")
    private ResponseEntity<?> process() {
        return ResponseEntity.ok(productService.executorServiceExample());
    }

    @GetAPIDocumentation
    @GetMapping("/list")
    @Operation(summary = "List of products.")
    public List<Product> getAllProducts() {
        return productService.saveProductList();
    }

    private Sort.Direction validateSortDirection(String sortDir) {
        try {
            return Sort.Direction.fromString(sortDir.toLowerCase());
        } catch (IllegalArgumentException e) {
            return Sort.Direction.ASC;
        }
    }

    private String validateSortBy(String sortBy) {
        if (!isFieldPresent(Product.class, sortBy)) {
            return "id";
        }
        return sortBy;
    }

    private boolean isFieldPresent(Class<?> clazz, String fieldName) {
        try {
            clazz.getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    private PaginatedResponse<ProductDTO> convertToPaginatedResponse(Page<Product> productPage) {
        List<ProductDTO> content = productPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        PaginatedResponse<ProductDTO> response = new PaginatedResponse<>();
        response.setContent(content);
        response.setCurrentPage(productPage.getNumber() + 1);
        response.setTotalItems(productPage.getTotalElements());
        response.setTotalPages(productPage.getTotalPages());
        return response;
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        return dto;
    }
}
