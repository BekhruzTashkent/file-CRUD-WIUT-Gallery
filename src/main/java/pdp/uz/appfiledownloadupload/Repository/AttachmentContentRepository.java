package pdp.uz.appfiledownloadupload.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pdp.uz.appfiledownloadupload.Entity.AttachmentContent;

import java.util.Optional;

public interface AttachmentContentRepository extends JpaRepository <AttachmentContent, Integer> {

      @Query(value = "select a from AttachmentContent a where a.attachment.id = ?1")
      Optional<AttachmentContent> findByAttachmentId(Integer attachment_id);


}
