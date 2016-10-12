package com.github.albertosh.adidas.backend.usecases.utils.storeimage;

import com.github.albertosh.adidas.backend.persistence.utils.IdGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import rx.Single;

@Singleton
public class StoreImageUseCase implements IStoreImageUseCase {

    private final static Dimension BOUNDARY = new Dimension(150, 150);

    private final IdGenerator idGenerator;
    private final File basePath;

    @Inject
    public StoreImageUseCase(IdGenerator idGenerator, @Named("storageBasePath") File storageBasePath) {
        this.idGenerator = idGenerator;
        this.basePath = storageBasePath;
    }

    private static Dimension getScaledDimension(Image image, Dimension boundary) {

        int original_width = image.getWidth(null);
        int original_height = image.getHeight(null);
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }

    @Override
    public Single<String> execute(StoreImageUseCaseInput input) {
        return Single.defer(() -> {
            // TODO check if the file is an image indeed

            File folder = new File(basePath, "images");
            if (!folder.exists())
                folder.mkdirs();
            String id = input.getImageId()
                    .orElseGet(() -> idGenerator.getNewId());
            File dest = new File(folder, id + ".jpg");

            try {
                BufferedImage image = ImageIO.read(input.getImage());
                Dimension dim = getScaledDimension(image, BOUNDARY);
                Image scaled = image.getScaledInstance(dim.width, dim.height, Image.SCALE_SMOOTH);

                BufferedImage bufferedScaled = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null), BufferedImage.TYPE_INT_RGB);
                bufferedScaled.getGraphics().drawImage(scaled, 0, 0, null);

                ImageIO.write(bufferedScaled, "jpg", dest);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return Single.just(id);
        });
    }

}
