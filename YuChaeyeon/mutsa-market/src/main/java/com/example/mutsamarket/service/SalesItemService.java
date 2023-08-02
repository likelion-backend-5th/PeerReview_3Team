package com.example.mutsamarket.service;

import com.example.mutsamarket.dto.ResponseDto;
import com.example.mutsamarket.dto.SalesItemDto;
import com.example.mutsamarket.entity.SalesItemEntity;
import com.example.mutsamarket.repository.SalesItemRepository;
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

    //create SalesItem
    public void createSalesItem(SalesItemDto dto) {
        log.info("createSalesItem 서비스 실행 시작");
        if (repository.existsById(dto.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        repository.save(dto.newEntity());
    }

    public Page<SalesItemDto> readSalesItemPaged(
            Integer pageNumber, Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(
                pageNumber, pageSize, Sort.by("id"));
        Page<SalesItemEntity> salesItemEntities = repository.findAll(pageable);
        return salesItemEntities.map(SalesItemDto::fromEntity);
    }

    public SalesItemDto readSalesItem(Long id) {
        Optional<SalesItemEntity> entityOptional = repository.findById(id);
        if (entityOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        else return SalesItemDto.fromEntity(entityOptional.get());
    }

    public ResponseDto updateSalesItem(Long id, SalesItemDto dto) {
        Optional<SalesItemEntity> entityOptional = repository.findById(id);
        if (entityOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        else {
            // 입력된 비밀번호가 맞는지 확인
            SalesItemEntity targetEntity = entityOptional.get();
            if (targetEntity.getPassword().equals(dto.getPassword())) {
                targetEntity.setTitle(dto.getTitle());
                targetEntity.setDescription(dto.getDescription());
                targetEntity.setMinPriceWanted(dto.getMinPriceWanted());
                targetEntity.setWriter(dto.getWriter());
                targetEntity.setPassword(dto.getPassword());
                SalesItemDto.fromEntity(repository.save(targetEntity));
                ResponseDto responseDto = new ResponseDto();
                responseDto.setMessage("물품이 수정되었습니다.");
                return responseDto;
            }
            else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseDto deleteSalesItem(Long id, SalesItemDto dto) {
        Optional<SalesItemEntity> optionalEntity = repository.findById(id);
        SalesItemEntity entity = optionalEntity.get();
        if (optionalEntity.isPresent() && entity.getPassword().equals(dto.getPassword())) {
            repository.deleteById(id);
            ResponseDto response = new ResponseDto();
            response.setMessage("물품을 삭제했습니다.");
            return response;
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

}