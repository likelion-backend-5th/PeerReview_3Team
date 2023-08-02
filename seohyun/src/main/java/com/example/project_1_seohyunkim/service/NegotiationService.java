package com.example.project_1_seohyunkim.service;

import com.example.project_1_seohyunkim.dto.NegotiationDto;
import com.example.project_1_seohyunkim.entity.NegotiationEntity;
import com.example.project_1_seohyunkim.entity.SalesItemEntity;
import com.example.project_1_seohyunkim.entity.UserEntity;
import com.example.project_1_seohyunkim.mapping.NegotiationsMapping;
import com.example.project_1_seohyunkim.repos.NegotiationRepository;
import com.example.project_1_seohyunkim.repos.SalesItemRepository;
import com.example.project_1_seohyunkim.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NegotiationService {
    private final NegotiationRepository negotiationRepository;
    private final SalesItemRepository salesItemRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public NegotiationDto createProposal(Long itemId, String username, String password, NegotiationDto dto) {
        Optional<SalesItemEntity> optionalSalesItemEntity = salesItemRepository.findById(itemId);
        if (optionalSalesItemEntity.isPresent()) {
            Optional<UserEntity> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                String userPw = user.get().getPassword();
                if (passwordEncoder.matches(password, userPw)) {
                    NegotiationEntity newProposal = NegotiationEntity.builder()
                            .id(dto.getId())
                            .suggestedPrice(dto.getSuggestedPrice())
                            .status(dto.getStatus())
                            .item(optionalSalesItemEntity.get())
                            .user(user.get()).build();
                    return NegotiationDto.fromEntity(negotiationRepository.save(newProposal));
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
    }

    public Page<NegotiationsMapping> readProposalsAll(
            Long itemId, String username, String password, Integer pageNumber) {
        Optional<SalesItemEntity> optionalSalesItemEntity = salesItemRepository.findById(itemId);

        if (optionalSalesItemEntity.isPresent()) {
            Optional<UserEntity> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                String userPw = user.get().getPassword();
                if (passwordEncoder.matches(password, userPw)) {
                    Pageable pageable = PageRequest.of(pageNumber, 25, Sort.by("id").ascending());
                    Page<NegotiationsMapping> negotiationsMappingPage = negotiationRepository.findAllProjectedByItemId(itemId, pageable);
                    return negotiationsMappingPage;
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
    }

    public NegotiationDto updateProposal(Long itemId, Long id, String username, String password, NegotiationDto dto) {
        Optional<NegotiationEntity> optionalNegotiationEntity = negotiationRepository.findByIdAndItemId(id, itemId);
        Optional<SalesItemEntity> optionalSalesItemEntity = salesItemRepository.findById(itemId);

        if (optionalNegotiationEntity.isPresent() && optionalSalesItemEntity.isPresent()) {
            NegotiationEntity negotiationEntity = optionalNegotiationEntity.get();
            SalesItemEntity salesItemEntity = optionalSalesItemEntity.get();
            Optional<UserEntity> user = userRepository.findByUsername(username);
            String userPw = user.get().getPassword();
            if (user.isPresent()) {
                if (dto.getStatus() != null
                        && (dto.getStatus().equals("수락") || dto.getStatus().equals("거절"))) {
                    if (passwordEncoder.matches(salesItemEntity.getUser().getPassword(), userPw)
                         && salesItemEntity.getUser().getUsername().equals(username)) {
                        negotiationEntity.setStatus(dto.getStatus());
                        negotiationRepository.save(negotiationEntity);
                        return NegotiationDto.fromEntity(negotiationEntity);
                        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
                    } else if (passwordEncoder.matches(negotiationEntity.getUser().getPassword(), userPw)
                                && negotiationEntity.getUser().getUsername().equals(username)) {
                    if (dto.getSuggestedPrice() != null) {
                        negotiationEntity.setSuggestedPrice(dto.getSuggestedPrice());
                    }
                    if (dto.getStatus() != null) {
                        // 이전 status가 "수락"이어야 현재 status를 "확정"으로 변경가능
                        if (dto.getStatus().equals("확정") && !negotiationEntity.getStatus().equals("수락")) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Another not accept");
                        } else negotiationEntity.setStatus(dto.getStatus());
                        salesItemEntity.setStatus("판매 완료");

                        // 현재 status가 "확정"이면 나머지 제안의 status를 "거절"로 변경
                        if (dto.getStatus().equals("확정")) {
                            List<NegotiationEntity> otherList = negotiationRepository.findByItemId(itemId);
                            for (NegotiationEntity entity : otherList) {
                                if (!entity.getStatus().equals("확정")) {
                                    entity.setStatus("거절");
                                }
                            }
                        }
                    }
                    negotiationRepository.save(negotiationEntity);
                    salesItemRepository.save(salesItemEntity);
                    return NegotiationDto.fromEntity(negotiationEntity);
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
    }

    public void deleteProposal(Long itemId, Long id, String username, String password) {
        Optional<NegotiationEntity> optionalNegotiationEntity = negotiationRepository.findByIdAndItemId(id, itemId);
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (optionalNegotiationEntity.isPresent()) {
            NegotiationEntity negotiationEntity = optionalNegotiationEntity.get();
            String userPw = user.get().getPassword();
            if (passwordEncoder.matches(password, userPw)) {
                if (negotiationEntity.getUser().getUsername().equals(username)) {
                    negotiationRepository.delete(negotiationEntity);
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect username");
            } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Negotiation not found");
    }
}
