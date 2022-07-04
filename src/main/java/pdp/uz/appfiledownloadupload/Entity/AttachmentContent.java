package pdp.uz.appfiledownloadupload.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AttachmentContent {//inside this table we have main content inside of file

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private byte[] asosiyContent;  //

    @OneToOne // EX: IT WORKS AS (select * rom attachment_content where attachment_id = 100)
    private Attachment attachment;  //for instance byte from video is only in one defined file

}
