package com.github.albertosh.adidas.backend.usecases.auth.register;

import com.github.albertosh.adidas.backend.models.user.AuthInfo;
import com.github.albertosh.adidas.backend.models.user.LoginInfo;
import com.github.albertosh.adidas.backend.models.user.User;
import com.github.albertosh.adidas.backend.persistence.authinfo.IAuthInfoPersistenceCreate;
import com.github.albertosh.adidas.backend.persistence.user.IUserPersistenceCreate;
import com.github.albertosh.adidas.backend.persistence.user.IUserPersistenceRead;
import com.github.albertosh.adidas.backend.persistence.utils.filter.Filter;
import com.github.albertosh.adidas.backend.usecases.utils.TokenGenerator;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import rx.Observable;
import rx.Single;

import static com.github.albertosh.adidas.backend.persistence.user.UserFilterFields.EMAIL;

@Singleton
public class RegisterUseCase
        implements IRegisterUseCase {

    private final IUserPersistenceRead userPersistenceRead;
    private final IUserPersistenceCreate userPersistenceCreate;
    private final IAuthInfoPersistenceCreate authInfoPersistenceCreate;
    private final TokenGenerator tokenGenerator;
    private final String defaultLanguage;

    @Inject
    public RegisterUseCase(IUserPersistenceRead userPersistenceRead,
                           IUserPersistenceCreate userPersistenceCreate,
                           IAuthInfoPersistenceCreate authInfoPersistenceCreate,
                           TokenGenerator tokenGenerator,
                           @Named("defaultLanguage") String defaultLanguage) {
        this.userPersistenceRead = userPersistenceRead;
        this.userPersistenceCreate = userPersistenceCreate;
        this.authInfoPersistenceCreate = authInfoPersistenceCreate;
        this.tokenGenerator = tokenGenerator;
        this.defaultLanguage = defaultLanguage;
    }

    @Override
    public Single<LoginInfo> execute(RegisterUseCaseInput input) {

        return userPersistenceRead.read(new Filter(EMAIL, input.getEmail()))
                .count()
                .flatMap(count -> {
                    if (count == 0) {
                        User.Builder userBuilder = new User.Builder()
                                .email(input.getEmail())
                                .firstName(input.getFirstName())
                                .lastName(input.getLastName())
                                .dateOfBirth(input.getDateOfBirth())
                                .country(input.getCountry())
                                .preferredLanguage(input.getPreferredLanguage().orElse(defaultLanguage));
                        return userPersistenceCreate.create(userBuilder).toObservable();
                    } else {
                        return Observable.error(RegisterUseCaseError.emailIsAlreadyRegistered);
                    }
                }).flatMap(user -> {
                    AuthInfo.Builder authInfoBuilder = new AuthInfo.Builder()
                            .uuid(user.getId())
                            .token(tokenGenerator.generate());
                    return authInfoPersistenceCreate.create(authInfoBuilder).toObservable();
                })
                .map(LoginInfo::new)
                .toSingle();
    }
}
