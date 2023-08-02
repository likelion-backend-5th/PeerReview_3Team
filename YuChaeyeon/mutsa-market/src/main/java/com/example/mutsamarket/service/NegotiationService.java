package com.example.mutsamarket.service;

import com.example.mutsamarket.dto.CommentDto;
import com.example.mutsamarket.dto.NegotiationDto;
import com.example.mutsamarket.entity.CommentEntity;
import com.example.mutsamarket.entity.NegotiationEntity;
import com.example.mutsamarket.entity.SalesItemEntity;
import com.example.mutsamarket.repository.NegotiationRepository;
import com.example.mutsamarket.repository.SalesItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NegotiationService {
    private final NegotiationRepository repository;
    private final SalesItemRepository itemRepository;

    public void createNegotiaion(Long itemId, NegotiationDto dto) {
        if (!itemRepository.existsById(itemId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        NegotiationEntity entity = new NegotiationEntity();
        entity.setWriter(dto.getWriter());
        entity.setPassword(dto.getPassword());
        entity.setStatus("제안");
        entity.setSuggestedPrice(dto.getSuggestedPrice());
        repository.save(entity);
    }

    public Page<NegotiationDto> readNegotiation(Long itemId, String writer, String password, Integer page) {

        // 물품 등록자인 경우
        // SalesRepository에서 itemId와 같은 salesItemEntity 찾은 후 매개값 writer와 일치하는지 확인.
        // true면 제안 목록 전체 조회. findAllbyId(itemId)
        Optional<SalesItemEntity> itemEntity = itemRepository.findById(itemId);
        SalesItemEntity salesItemEntity = itemEntity.get();
        if (itemEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);



        // 제안 등록자인 경우
        // negotiationRepository에서 findByitemId로 찾은 후
        // 해당 entity의 작성자와 매개값 writer, password 일치하는지 확인.



        Pageable pageable = PageRequest.of(
                page, 25, Sort.by("id"));
        Page<NegotiationEntity> negotiationEntities = repository.findAll(pageable);
        return negotiationEntities.map(NegotiationDto::fromEntity);
    }

//    public void updateNegotiation(Long itemId, Long id, NegotiationDto dto) {
//        Optional<NegotiationEntity>
//    }

    public void updateNegotiation(Long itemId,  Long negotiationId, NegotiationDto dto) {
        Optional<SalesItemEntity> optionalSalesItem = itemRepository.findById(itemId);
        Optional<NegotiationEntity> optionalNegotiation = repository.findById(negotiationId);

        // 존재하지 않으면 예외 발생
        if (optionalSalesItem.isEmpty() && optionalNegotiation.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // 아니면 로직 진행
        NegotiationEntity targetEntity = optionalNegotiation.get();

        // 대상 댓글이 대상 게시글의 댓글이 맞는지
        if (!targetEntity.getPassword().equals(dto.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        targetEntity.setWriter(dto.getWriter());
        targetEntity.setSuggestedPrice(dto.getSuggestedPrice());
        NegotiationDto.fromEntity(repository.save(targetEntity));
    }

    public void deleteNegotiation(Long itemId,  Long negotiationId, NegotiationDto dto) {
        Optional<SalesItemEntity> optionalSalesItem = itemRepository.findById(itemId);
        Optional<NegotiationEntity> optionalNegotiation = repository.findById(negotiationId);

        // 존재하지 않으면 예외 발생
        if (optionalSalesItem.isEmpty() && optionalNegotiation.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // 아니면 로직 진행
        NegotiationEntity targetEntity = optionalNegotiation.get();

        // 대상 댓글이 대상 게시글의 댓글이 맞는지
        if (!targetEntity.getPassword().equals(dto.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        repository.deleteById(negotiationId);
    }
}
