package com.urlshortener.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlRequest {
    
    @NotBlank(message = "URL cannot be empty")
    @Pattern(regexp = "^(https?|ftp)://[\\w-]+(\\.[\\w-]+)+([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?$", 
            message = "Invalid URL format. Please provide a valid URL starting with http:// or https://")
    private String url;
} 