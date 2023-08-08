package com.example.mutsamarket.controller;

import com.example.mutsamarket.dto.ResponseDto;
import com.example.mutsamarket.dto.SalesItemDto;
import com.example.mutsamarket.service.SalesItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class SalesItemController {

    private final SalesItemService service;

    // POST /items
    @PostMapping
    public ResponseDto createSalesItem(@RequestBody SalesItemDto dto) {
        log.info("createSalesItem 매핑 완료");
        service.createSalesItem(dto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("등록이 완료되었습니다.");
        return responseDto;
    }

    // GET /items?page={page}&limit={limit}
    @GetMapping
    public Page<SalesItemDto> readSalesItemPagedAll(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "limit", defaultValue = "25") Integer pageSize
    ) {
        return service.readSalesItemPaged(pageNumber, pageSize);
    }

    // GET /items/{itemId}
    @GetMapping("/{itemId}")
    public SalesItemDto readSalesItem(@PathVariable("itemId") Long itemId) {
        return service.readSalesItem(itemId);
    }

    // PUT /items/{itemId}
    @PutMapping("/{itemId}")
    public ResponseDto updateSalesItem(
            @PathVariable("itemId") Long id,
            @RequestBody SalesItemDto dto
    ) {
        return service.updateSalesItem(id, dto);
    }

    // PUT /items/{itemId}/image

    // DELETE /items/{itemId}
    @DeleteMapping("/{itemId}")
    public ResponseDto deleteSalesItem(
            @PathVariable("itemId") Long id,
            @RequestBody SalesItemDto dto
    ) {
        return service.deleteSalesItem(id, dto);
    }
}
