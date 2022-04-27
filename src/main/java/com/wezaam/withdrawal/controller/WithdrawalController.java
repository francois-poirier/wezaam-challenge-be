package com.wezaam.withdrawal.controller;

import com.wezaam.withdrawal.dto.WithdrawalDtoList;
import com.wezaam.withdrawal.service.WithdrawalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "withdrawal", description = "the Withdrawal API")
@Validated
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    @Autowired
    public WithdrawalController(final WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;

    }

    @Operation(summary = "Find all withdrawals", description = "Find all withdrawals", tags = { "withdrawal" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = WithdrawalDtoList.class)))) })
    @GetMapping("/api/v1/withdrawals")
    public ResponseEntity<WithdrawalDtoList> withdrawals() {
        return ResponseEntity.status(HttpStatus.OK).body(withdrawalService.findAll());
    }
}
