
package com.ucms.ucmsapi.storage;

import java.io.InputStream;

public interface FileStorage {
    /**
     * Stores the stream and returns a storage key (relative path or opaque id).
     */
    String store(InputStream in, String originalName, long size, String folder);

    /**
     * Loads a file as InputStream for reading (must exist).
     */
    InputStream load(String key);

    /**
     * Deletes a file if exists (best-effort).
     */
    void delete(String key);
}
