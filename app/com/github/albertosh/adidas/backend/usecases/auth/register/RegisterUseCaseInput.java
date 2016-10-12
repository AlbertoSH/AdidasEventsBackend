package com.github.albertosh.adidas.backend.usecases.auth.register;

import com.google.common.base.Preconditions;

import java.time.LocalDate;
import java.util.Optional;

public class RegisterUseCaseInput {

    private final String email;
    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final String country;
    private final String preferredLanguage;

    private RegisterUseCaseInput(Builder builder) {
        this.email = Preconditions.checkNotNull(builder.email);
        this.firstName = Preconditions.checkNotNull(builder.firstName);
        this.lastName = Preconditions.checkNotNull(builder.lastName);
        this.dateOfBirth = Preconditions.checkNotNull(builder.dateOfBirth);
        this.country = Preconditions.checkNotNull(builder.country);
        this.preferredLanguage = builder.preferredLanguage;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public Optional<String> getPreferredLanguage() {
        return Optional.ofNullable(preferredLanguage);
    }


    public static class Builder {
        private String email;
        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;
        private String country;
        private String preferredLanguage;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder preferredLanguage(String preferredLanguage) {
            this.preferredLanguage = preferredLanguage;
            return this;
        }

        public Builder fromPrototype(RegisterUseCaseInput prototype) {
            email = prototype.email;
            firstName = prototype.firstName;
            lastName = prototype.lastName;
            dateOfBirth = prototype.dateOfBirth;
            country = prototype.country;
            preferredLanguage = prototype.preferredLanguage;
            return this;
        }

        public RegisterUseCaseInput build() {
            return new RegisterUseCaseInput(this);
        }
    }
}
