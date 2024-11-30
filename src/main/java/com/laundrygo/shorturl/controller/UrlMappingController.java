package com.laundrygo.shorturl.controller;

import com.laundrygo.shorturl.common.advice.ApiResponse;
import com.laundrygo.shorturl.controller.request.ShortUrlGenerateRequest;
import com.laundrygo.shorturl.service.UrlMappingService;
import com.laundrygo.shorturl.service.response.SavedOriginalUrlResponse;
import com.laundrygo.shorturl.service.response.SavedShortUrlResponse;
import com.laundrygo.shorturl.service.response.UrlMappingsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class UrlMappingController {

    private final UrlMappingService urlMappingService;

    @PostMapping("/generator")
    public ApiResponse<SavedShortUrlResponse> generateShortUrl(@Valid @RequestBody ShortUrlGenerateRequest request) {
        return ApiResponse.success(urlMappingService.generateShortUrl(request.getOriginalUrl()));
    }

    @GetMapping("/resolver")
    public ApiResponse<SavedOriginalUrlResponse> resolveShortUrl(@RequestParam String shortUrl) {
        return ApiResponse.success(urlMappingService.getOriginalUrl(shortUrl));
    }

    @GetMapping("/urls")
    public ApiResponse<UrlMappingsResponse> getUrls() {
        return ApiResponse.success(urlMappingService.getAllUrlMappings());
    }

}
