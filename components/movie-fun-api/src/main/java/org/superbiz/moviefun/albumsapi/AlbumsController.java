package org.superbiz.moviefun.albumsapi;

import org.apache.tika.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.superbiz.moviefun.blobstore.Blob;
import org.superbiz.moviefun.blobstore.BlobStore;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

@Controller
@RequestMapping("/albums")
public class AlbumsController {
    public static final Logger logger = LoggerFactory.getLogger(AlbumsController.class);
    private final AlbumsClient albumsClient;
    private final BlobStore blobStore;

    public AlbumsController(AlbumsClient albumsClient, BlobStore blobStore) {
        this.albumsClient = albumsClient;
        this.blobStore = blobStore;
    }

    @GetMapping
    public String index(Map<String, Object> model) {
        model.put("albums", albumsClient.getAlbums());
        return "albums";
    }

    @GetMapping("/{albumId}")
    public String details(@PathVariable long albumId, Map<String, Object> model) {
        model.put("album", albumsClient.find(albumId));
        return "albumDetails";
    }

    @PostMapping("/{albumId}/cover")
    public String uploadCover(@PathVariable Long albumId, @RequestParam("file") MultipartFile uploadedFile) {
        logger.debug("Uploading cover for album with id {}", albumId);

        if (uploadedFile.getSize() > 0) {
            try {
                tryToUploadCover(albumId, uploadedFile);

            } catch (IOException e) {
                logger.warn("Error while uploading album cover", e);
            }
        }

        return format("redirect:/albums/%d", albumId);
    }

    @GetMapping("/{albumId}/cover")
    public ResponseEntity<byte[]> getCover(@PathVariable long albumId) throws IOException, URISyntaxException {
        Optional<Blob> maybeCoverBlob = blobStore.get(getCoverBlobName(albumId));

        if ( maybeCoverBlob.isPresent() ) {
            Blob coverBlob = maybeCoverBlob.get();
            byte[] imageBytes = IOUtils.toByteArray(coverBlob.inputStream);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(coverBlob.contentType));
            headers.setContentLength(imageBytes.length);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    private void tryToUploadCover(@PathVariable Long albumId, @RequestParam("file") MultipartFile uploadedFile) throws IOException {
        Blob coverBlob = new Blob(
                getCoverBlobName(albumId),
                uploadedFile.getInputStream(),
                uploadedFile.getContentType()
        );

        blobStore.put(coverBlob);
    }

    private String getCoverBlobName(@PathVariable long albumId) {
        return format("covers/%d", albumId);
    }
}
