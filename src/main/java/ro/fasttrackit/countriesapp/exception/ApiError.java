package ro.fasttrackit.countriesapp.exception;

import lombok.Builder;

@Builder
public record ApiError(
        String message,
        int entityId
) {
}
