package com.example.project_1_seohyunkim.controller;

import com.example.project_1_seohyunkim.BaseResponse;
import com.example.project_1_seohyunkim.ResponseMessage;
import com.example.project_1_seohyunkim.dto.NegotiationDto;
import com.example.project_1_seohyunkim.mapping.NegotiationsMapping;
import com.example.project_1_seohyunkim.service.NegotiationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/items/{itemId}/proposals")
@RequiredArgsConstructor
public class NegotiationController {
    private final NegotiationService negotiationService;

    @PostMapping
    public BaseResponse<String> create(
            @PathVariable("itemId") Long itemId,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @Valid @RequestBody NegotiationDto dto) {
        negotiationService.createProposal(itemId, username, password, dto);
        return new BaseResponse<>(ResponseMessage.CREATE_PROPOSAL);
    }

    @GetMapping
    public Page<NegotiationsMapping> readAll(
            @PathVariable("itemId") Long itemId,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber) {
        return negotiationService.readProposalsAll(itemId, username, password, pageNumber);
    }

    @PutMapping("/{proposalId}")
    public BaseResponse<String> update(
            @PathVariable("itemId") Long itemId,
            @PathVariable("proposalId") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody NegotiationDto dto) {
        negotiationService.updateProposal(itemId, id, username, password, dto);
        if (dto.getStatus() != null) {
            if (dto.getStatus().equals("확정")) {
                return new BaseResponse<>(ResponseMessage.UPDATE_PROPOSAL_CONFIRMED);
            } else return new BaseResponse<>(ResponseMessage.UPDATE_PROPOSAL_STATUS);
        }  return new BaseResponse<>(ResponseMessage.UPDATE_PROPOSAL);
    }

    @DeleteMapping("/{proposalId}")
    public BaseResponse<String> delete(
            @PathVariable("itemId") Long itemId,
            @PathVariable("proposalId") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        negotiationService.deleteProposal(itemId, id, username, password);
        return new BaseResponse<>(ResponseMessage.DELETE_PROPOSAL);
    }
}