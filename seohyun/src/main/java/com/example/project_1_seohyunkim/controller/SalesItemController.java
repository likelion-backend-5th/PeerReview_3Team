package com.example.project_1_seohyunkim.controller;

import com.example.project_1_seohyunkim.BaseResponse;
import com.example.project_1_seohyunkim.ResponseMessage;
import com.example.project_1_seohyunkim.dto.SalesItemDto;
import com.example.project_1_seohyunkim.mapping.ItemsMapping;
import com.example.project_1_seohyunkim.service.SalesItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class SalesItemController {

    private final SalesItemService salesItemService;

    @PostMapping
    public BaseResponse<String> create(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @Valid @RequestBody SalesItemDto dto) {
        salesItemService.createItem(username, password, dto);
        return new BaseResponse<>(ResponseMessage.CREATE_ITEM);
    }

    @GetMapping("/{itemId}")
    public SalesItemDto read(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @PathVariable("itemId") Long id) {
        return salesItemService.readItem(id, username, password);
    }


    @GetMapping
    public Page<ItemsMapping> readAll(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "limit", defaultValue = "25") Integer pageSize) {
        return salesItemService.readItemsAll(pageNumber, pageSize);
    }

    @PutMapping("/{itemId}")
    public BaseResponse<String> update(
            @PathVariable("itemId") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody SalesItemDto dto) {
        salesItemService.updateItem(id, username, password, dto);
        return new BaseResponse<>(ResponseMessage.UPDATE_ITEM);
    }

    @PutMapping("/{itemId}/image")
    public BaseResponse<String> uploadItemImage(
            @PathVariable("itemId") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestPart("image") MultipartFile imageFile) throws IOException {
        salesItemService.uploadImage(id, username, password, imageFile);
        return new BaseResponse<>(ResponseMessage.CREATE_IMAGE);
    }

    @DeleteMapping("/{itemId}")
    public BaseResponse<String> delete(
            @PathVariable("itemId") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password){
        salesItemService.deleteItem(id, username, password);
        return new BaseResponse<>(ResponseMessage.DELETE_ITEM);
    }
}
