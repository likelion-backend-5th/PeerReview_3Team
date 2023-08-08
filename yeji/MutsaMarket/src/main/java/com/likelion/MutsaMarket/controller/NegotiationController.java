package com.likelion.MutsaMarket.controller;

import com.likelion.MutsaMarket.dto.CommentDto;
import com.likelion.MutsaMarket.dto.NegotiationDto;
import com.likelion.MutsaMarket.dto.ResponseDto;
import com.likelion.MutsaMarket.service.NegotiationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items/{itemId}/proposals")
public class NegotiationController {
    private final NegotiationService service;
    private final ResponseDto responseDto = new ResponseDto();

    // POST /items/{itemId}/proposals
    @PostMapping
    public ResponseDto create(
            @PathVariable("itemId") Long itemId,
            @RequestBody NegotiationDto dto) {
        service.createNegotiation(itemId, dto);
        responseDto.setMessage("구매 제안이 등록되었습니다.");
        return responseDto;
    }

    // GET /items/{itemId}/proposals?writer=jeeho.edu&password=qwerty1234&page=1


    // PUT /items/{itemId}/proposals/{proposalId}
    @PutMapping("/{proposalId}")
    public ResponseDto update(
            @PathVariable("itemId") Long itemId,  // URL의 ID
            @PathVariable("proposalId") Long negotiationId,
            @RequestBody NegotiationDto dto   // HTTP Request Body
    ) {
        ResponseDto responseDto = new ResponseDto();
        service.updateNegotiation(itemId, negotiationId, dto);
        responseDto.setMessage("제안이 수정되었습니다.");
        return responseDto;
    }
    
    // DELETE /items/{itemId}/proposals/{proposalId}
    @DeleteMapping("/{proposalId}")
    public ResponseDto delete(
            @PathVariable("itemId") Long itemId,  // URL의 ID
            @PathVariable("proposalId") Long negotiationId,
            @RequestBody NegotiationDto dto   // HTTP Request Body
    ) {
        ResponseDto responseDto = new ResponseDto();
        service.deleteNegotiation(itemId, negotiationId, dto);
        responseDto.setMessage("제안이 삭제되었습니다.");
        return responseDto;
    }

    // PUT /items/{itemId}/proposals/{proposalId}
    // PUT /items/{itemId}/proposals/{proposalId}

}
