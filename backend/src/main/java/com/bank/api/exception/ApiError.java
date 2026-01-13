package com.bank.api.exception;

import java.time.Instant;

public record ApiError(
        String message,
        String code,
        Instant timestamp,
        String path
) {}
