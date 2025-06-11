package com.pieceofcake.paymentservice.money.dto.in;

import com.pieceofcake.paymentservice.money.vo.in.WithdrawMoneyRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WithdrawMoneyRequestDto {
    private String memberUuid;
    private String bank;
    private Long amount;
    private String accountHolderName;
    private String accountNumber;

    @Builder
    public WithdrawMoneyRequestDto(
            String memberUuid,
            String bank,
            Long amount,
            String accountHolderName,
            String accountNumber
    ) {
        this.memberUuid = memberUuid;
        this.bank = bank;
        this.amount = amount;
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
    }

    public static WithdrawMoneyRequestDto from(WithdrawMoneyRequestVo withdrawMoneyRequestVo, String memberUuid) {
        return WithdrawMoneyRequestDto.builder()
                .memberUuid(memberUuid)
                .bank(withdrawMoneyRequestVo.getBank())
                .amount(withdrawMoneyRequestVo.getAmount())
                .accountHolderName(withdrawMoneyRequestVo.getAccountHolderName())
                .accountNumber(withdrawMoneyRequestVo.getAccountNumber())
                .build();
    }
}
