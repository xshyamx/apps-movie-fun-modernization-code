package org.superbiz.moviefun.albums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.superbiz.moviefun.blobstore.BlobStore;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AlbumsRepository albumsRepository;
    private final BlobStore blobStore;

    public AlbumsController(AlbumsRepository albumsRepository, BlobStore blobStore) {
        this.albumsRepository = albumsRepository;
        this.blobStore = blobStore;
    }

    @PostMapping
    public void add(@RequestBody Album album) {
        logger.debug("album-service add({})", album);
        albumsRepository.addAlbum(album);
    }

    @GetMapping
    public List<Album> index() {
        return albumsRepository.getAlbums();
    }

    @GetMapping("/{albumId}")
    public Album details(@PathVariable long albumId) {
        return albumsRepository.find(albumId);
    }

}
