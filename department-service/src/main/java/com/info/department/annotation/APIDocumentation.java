package com.info.department.annotation;

import com.info.department.model.Department;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Department.class))
        ),
        @ApiResponse(
                description = "Unauthorized",
                responseCode = "401",
                content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Forbidden",
                content = @Content
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Not Found",
                content = @Content
        )
})
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface APIDocumentation {
}
