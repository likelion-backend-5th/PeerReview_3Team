package com.likelion.MutsaMarket.service;

import com.likelion.MutsaMarket.dto.ResponseDto;
import com.likelion.MutsaMarket.dto.SalesItemDto;
import com.likelion.MutsaMarket.entity.SalesItemEntity;
import com.likelion.MutsaMarket.repository.SalesItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalesItemService {
    private final SalesItemRepository repository;

    // create
    public SalesItemDto createSalesItem(SalesItemDto dto) {
        SalesItemEntity newSalesItem = new SalesItemEntity();
        newSalesItem.setTitle(dto.getTitle());
        newSalesItem.setDescription(dto.getDescription());
        newSalesItem.setMinPriceWanted(dto.getMinPriceWanted());
        newSalesItem.setStatus("판매중");
        newSalesItem.setWriter(dto.getWriter());
        newSalesItem.setPassword(dto.getPassword());

        return SalesItemDto.fromEntity(repository.save(newSalesItem));
    }

    // read pages
    public Page<SalesItemDto> readSalesItemPaged(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        Page<SalesItemEntity> salesItemEntity = repository.findAll(pageable);
        return salesItemEntity.map(SalesItemDto::fromEntity);
    }

    // read all
    public List<SalesItemDto> readSalesItemAll() {
        List<SalesItemDto> salesItemList = new ArrayList<>();
        for (SalesItemEntity entity: repository.findAll()) {
            salesItemList.add(SalesItemDto.fromEntity(entity));
        }
        return salesItemList;
    }

    // read
    public SalesItemDto readSalesItem(Long id) {
        Optional<SalesItemEntity> optionalSalesItem = repository.findById(id);
        if (optionalSalesItem.isPresent())
            return SalesItemDto.fromEntity(optionalSalesItem.get());
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // update
    public SalesItemDto updateSalesItem(Long id, SalesItemDto dto) {
        Optional<SalesItemEntity> optionalSalesItem = repository.findById(id);
        if (optionalSalesItem.isPresent()) {
            SalesItemEntity targetEntity = optionalSalesItem.get();
            if (targetEntity.getPassword().equals(dto.getPassword())) {
                targetEntity.setTitle(dto.getTitle());
                targetEntity.setDescription(dto.getDescription());
                targetEntity.setMinPriceWanted(dto.getMinPriceWanted());
                targetEntity.setWriter(dto.getWriter());
                targetEntity.setPassword(dto.getPassword());
                return SalesItemDto.fromEntity(repository.save(targetEntity));
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // delete
    public ResponseDto deleteSalesItem(Long id, SalesItemDto dto) {
        Optional<SalesItemEntity> optionalSalesItem = repository.findById(id);
        if (optionalSalesItem.isPresent()) {
            SalesItemEntity targetEntity = optionalSalesItem.get();
            if (targetEntity.getPassword().equals(dto.getPassword())) {
                repository.deleteById(id);
                ResponseDto responseDto = new ResponseDto();
                responseDto.setMessage("물품을 삭제했습니다.");
                return responseDto;
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}