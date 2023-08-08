package com.likelion.MutsaMarket.controller;

import com.likelion.MutsaMarket.dto.ResponseDto;
import com.likelion.MutsaMarket.dto.SalesItemDto;
import com.likelion.MutsaMarket.service.SalesItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    public ResponseDto create(@RequestBody SalesItemDto dto) {
        ResponseDto responseDto = new ResponseDto();
        service.createSalesItem(dto);
        responseDto.setMessage("등록이 완료되었습니다.");
        log.info("POST /items");
        return responseDto;
    }

    // GET /items?page={page}&limit={limit}
    @GetMapping
    public Page<SalesItemDto> readSalesItemAll(
            @RequestParam(value = "page", defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "limit", defaultValue = "25") Integer pageSize
    ) {
        return service.readSalesItemPaged(pageNumber, pageSize);
    }

    // GET /items
//    @GetMapping
//    public List<SalesItemDto> readSalesItemAll() {
//        return service.readSalesItemAll();
//    }

    // GET /items/{itemId}
    @GetMapping("/{id}")
    public SalesItemDto read(@PathVariable("id") Long id) {
        return service.readSalesItem(id);
    }

    // PUT /items/{itemId}
    @PutMapping("/{id}")
    public ResponseDto update(
            @PathVariable("id") Long id,
            @RequestBody SalesItemDto dto) {
        ResponseDto responseDto = new ResponseDto();
        service.updateSalesItem(id, dto);
        responseDto.setMessage("물품이 수정되었습니다.");
        log.info("PUT /items");
        return responseDto;
    }

    // PUT /items/{itemId}/image

    // DELETE /items/{itemId}
    @DeleteMapping("/{id}")
    public ResponseDto delete(
            @PathVariable("id") Long id,
            @RequestBody SalesItemDto dto) {
        log.info("DELETE /items");
        return service.deleteSalesItem(id, dto);
    }
}
