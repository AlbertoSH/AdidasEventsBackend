package com.github.albertosh.adidas.backend.usecases.auth.login;

import com.github.albertosh.adidas.backend.models.user.AuthInfo;
import com.github.albertosh.adidas.backend.models.user.LoginInfo;
import com.github.albertosh.adidas.backend.persistence.authinfo.IAuthInfoPersistenceCreate;
import com.github.albertosh.adidas.backend.persistence.user.IUserPersistenceRead;
import com.github.albertosh.adidas.backend.persistence.utils.filter.Filter;
import com.github.albertosh.adidas.backend.usecases.utils.PasswordStorage;
import com.github.albertosh.adidas.backend.usecases.utils.TokenGenerator;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Single;

import static com.github.albertosh.adidas.backend.persistence.user.UserFilterFields.EMAIL;
import static com.github.albertosh.adidas.backend.persistence.user.UserFilterFields.ENCODED_PASSWORD;

@Singleton
public class LoginUseCase
        implements ILoginUseCase {

    private final IUserPersistenceRead userPersistenceRead;
    private final IAuthInfoPersistenceCreate authInfoPersistenceCreate;
    private final TokenGenerator tokenGenerator;
    private final PasswordStorage passwordStorage;

    @Inject
    public LoginUseCase(IUserPersistenceRead userPersistenceRead,
                        IAuthInfoPersistenceCreate authInfoPersistenceCreate,
                        TokenGenerator tokenGenerator, PasswordStorage passwordStorage) {
        this.userPersistenceRead = userPersistenceRead;
        this.authInfoPersistenceCreate = authInfoPersistenceCreate;
        this.tokenGenerator = tokenGenerator;
        this.passwordStorage = passwordStorage;
    }

    @Override
    public Single<LoginInfo> execute(LoginUseCaseInput input) {
        try {
            String encodedPassword = passwordStorage.createHash(input.getPassword());

            Filter filter = new Filter(EMAIL, input.getEmail())
                    .and(ENCODED_PASSWORD, encodedPassword);
            return userPersistenceRead.read(filter)
                    .toList()
                    .flatMap(list -> {
                        if (list.isEmpty()) {
                            return Observable.error(LoginUseCaseError.incorrectUserOrPassword);
                        } else {
                            return Observable.just(list.get(0));
                        }
                    })
                    .flatMap(user -> {
                        AuthInfo.Builder authInfoBuilder = new AuthInfo.Builder()
                                .uuid(user.getId())
                                .token(tokenGenerator.generate());
                        return authInfoPersistenceCreate.create(authInfoBuilder).toObservable();
                    })
                    .map(LoginInfo::new)
                    .toSingle();

        } catch (PasswordStorage.CannotPerformOperationException e) {
            //Shouldn't happen...
            throw new RuntimeException(e);
        }
    }
}
