package com.wezaam.withdrawal.controller;

import com.wezaam.withdrawal.dto.UserDto;
import com.wezaam.withdrawal.dto.UserDtoList;
import com.wezaam.withdrawal.dto.WithdrawalDto;
import com.wezaam.withdrawal.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@Tag(name = "user", description = "the User API")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;

    }

    @Operation(summary = "Find all users", description = "Find all users", tags = { "user" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDtoList.class)))) })
    @GetMapping("/api/v1/users")
    public ResponseEntity<UserDtoList> users() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @Operation(summary = "Find user by id", description = "Returns a user ", tags = { "user" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @GetMapping("/api/v1/users/{userUuid}")
    public ResponseEntity<UserDto> user(@Parameter(description = "Id. Cannot be null.", required = true)@NotNull(message = "userUuid cannot be null") @PathVariable UUID userUuid) {
        return userService.find(userUuid).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create withdrawal", description = "", tags = { "withdrawal" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create withdrawal"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @PostMapping("/api/v1/users/{userUuid}/withdrawals")
    public ResponseEntity<Void> create(@Parameter(description = "Id. Cannot be null.", required = true) @NotNull(message = "userUuid cannot be null") @PathVariable UUID userUuid,
                                       @Parameter(description= "Create withdrawal. Cannot null or empty.",
            required=true, schema=@Schema(implementation = WithdrawalDto.class)) @Valid @RequestBody WithdrawalDto withdrawalDto) {
        userService.createWithdrawal(userUuid,withdrawalDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
