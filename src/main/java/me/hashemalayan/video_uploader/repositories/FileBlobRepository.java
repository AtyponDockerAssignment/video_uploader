package me.hashemalayan.video_uploader.repositories;

import me.hashemalayan.video_uploader.domain.FileBlob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileBlobRepository extends JpaRepository<FileBlob, Long> {
}
