package com.example.mutsamarket.controller;

import com.example.mutsamarket.dto.CommentDto;
import com.example.mutsamarket.dto.NegotiationDto;
import com.example.mutsamarket.dto.ResponseDto;
import com.example.mutsamarket.service.NegotiationService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items/{itemId}/proposals")
public class NegotiationController {
    private final NegotiationService service;

    // POST /items/{itemId}/proposals
    @PostMapping
    public ResponseDto createProposal(
            @PathVariable("itemId") Long id,
            @RequestBody NegotiationDto dto) {
        service.createNegotiaion(id, dto);
        ResponseDto response = new ResponseDto();
        response.setMessage("구매 제안이 등록되었습니다.");
        return response;
    }

//     GET /items/{itemId}/proposals?writer=jeeho.edu&password=qwerty1234&page=1
    @GetMapping
    public void readProposal(
            @PathVariable("itemId") Long id,
            @RequestParam(value = "writer") String writer,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "page") Integer pageNumber
    ) {

    }

    // PUT /items/{itemId}/proposals/{proposalId}
    @PutMapping("/{proposalId}")
    public ResponseDto updateProposal(
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

    // PU


}
