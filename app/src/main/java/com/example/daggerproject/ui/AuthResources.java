package com.example.daggerproject.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AuthResources<T> {

    @NonNull
    public final AuthStatus status;
    @Nullable
    public final T data;
    @Nullable
    public final String message;

    public AuthResources(@NonNull AuthStatus status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> AuthResources<T> authenticated(@Nullable T data) {
        return new AuthResources<>(AuthStatus.AUTHENTICATED, data, null);
    }

    public static <T> AuthResources<T> error(@NonNull String message, @Nullable T data) {
        return new AuthResources<>(AuthStatus.ERROR, data, message);
    }

    public static <T> AuthResources<T> loading(@Nullable T data) {
        return new AuthResources<>(AuthStatus.LOADING, data, null);
    }

    public static <T> AuthResources<T> notAuthenticated() {
        return new AuthResources<>(AuthStatus.NOT_AUTHENTICATED, null, null);
    }

    public enum AuthStatus {AUTHENTICATED, ERROR, LOADING, NOT_AUTHENTICATED}
}
