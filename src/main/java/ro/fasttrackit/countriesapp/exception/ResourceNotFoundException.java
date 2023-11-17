package ro.fasttrackit.countriesapp.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final int entityId;

    public ResourceNotFoundException(String message, int entityId) {
        super(message);
        this.entityId = entityId;
    }
}
