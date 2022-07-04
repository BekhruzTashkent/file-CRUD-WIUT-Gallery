package pdp.uz.appfiledownloadupload.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pdp.uz.appfiledownloadupload.Entity.Attachment;

public interface AttachmentRepository extends JpaRepository <Attachment, Integer> {


}
