package ro.fasttrackit.countriesapp.exception;

import lombok.Builder;

@Builder
public record ApiError(
        String message,
        long entityId
) {
}
