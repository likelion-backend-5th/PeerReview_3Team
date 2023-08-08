package com.likelion.MutsaMarket.service;

import com.likelion.MutsaMarket.dto.CommentDto;
import com.likelion.MutsaMarket.dto.NegotiationDto;
import com.likelion.MutsaMarket.entity.CommentEntity;
import com.likelion.MutsaMarket.entity.NegotiationEntity;
import com.likelion.MutsaMarket.entity.SalesItemEntity;
import com.likelion.MutsaMarket.repository.NegotiationRepository;
import com.likelion.MutsaMarket.repository.SalesItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NegotiationService {
    private final NegotiationRepository negotiationRepository;
    private final SalesItemRepository itemRepository;
    private final SalesItemService itemService;

    // create
    public NegotiationDto createNegotiation(Long itemId, NegotiationDto dto) {
        if (!itemRepository.existsById(itemId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        NegotiationEntity newNegotiation = new NegotiationEntity();
        newNegotiation.setItemId(itemId);
        newNegotiation.setWriter(dto.getWriter());
        newNegotiation.setPassword(dto.getPassword());
        newNegotiation.setSuggestedPrice(dto.getSuggestedPrice());
        newNegotiation.setStatus("제안중");
        return NegotiationDto.fromEntity(negotiationRepository.save(newNegotiation));
    }

    // read pages
//    @GetMapping
//    public Page<NegotiationDto> readNegotiationPaged(
//            Long itemId, String writer, String password, Integer pageNume, Integer pageSize) {
//        if (!negotiationRepository.existsById(itemId))
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        Pageable pageable = PageRequest.of(0, 25, Sort.by("id"));
//        Page<CommentEntity> commentEntity = commentRepository.findAll(pageable);
//        return commentEntity.map(CommentDto::fromEntity);
//    }

    // update
    public void updateNegotiation(Long itemId,  Long negotiationId, NegotiationDto dto) {
        Optional<SalesItemEntity> optionalSalesItem = itemRepository.findById(itemId);
        Optional<NegotiationEntity> optionalNegotiation = negotiationRepository.findById(negotiationId);

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
        NegotiationDto.fromEntity(negotiationRepository.save(targetEntity));
    }

    // delete
    public void deleteNegotiation(Long itemId,  Long negotiationId, NegotiationDto dto) {
        Optional<SalesItemEntity> optionalSalesItem = itemRepository.findById(itemId);
        Optional<NegotiationEntity> optionalNegotiation = negotiationRepository.findById(negotiationId);

        // 존재하지 않으면 예외 발생
        if (optionalSalesItem.isEmpty() && optionalNegotiation.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // 아니면 로직 진행
        NegotiationEntity targetEntity = optionalNegotiation.get();

        // 대상 댓글이 대상 게시글의 댓글이 맞는지
        if (!targetEntity.getPassword().equals(dto.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        negotiationRepository.deleteById(negotiationId);
    }
}
