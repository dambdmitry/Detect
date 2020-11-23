package edu.lab.s3;

import edu.lab.exceptions.storage.CannotDeleteFileException;
import edu.lab.exceptions.storage.NoSuchFileInStorageException;
import org.springframework.web.multipart.MultipartFile;

/*
 * Интерфейс взаимодествия приложения с хранилищем файлов
 */
public interface PhotoStorage {
    /**
     *
     * @param file файл который надо сохранить в хранилище.
     * @return путь доступа к этому файлу, может быть например url.
     */
    String save(MultipartFile file);

    /**
     * @throws CannotDeleteFileException если не удалось удалить, или найти файл по ключу.
     * @param key ключ к файлу, откуда его брать.
     */
    void delete(String key);

    /**
     *
     * @throws NoSuchFileInStorageException если не удалость найти файл по таким параметрам.
     * @param bucket bucket в S3.
     * @param key key в S3.
     * @return url файла.
     */
    String getFileUrl(String bucket, String key);

    String getBucket();
}
