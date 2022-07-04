package pdp.uz.appfiledownloadupload.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//FOR UPLOAD IN DB WE NEED TWO TABLES
//it is first table
public class Attachment { //this table used to show main info about file

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String fileOriginalName; //pdp.jpg

    private long size;  //size of file ex: 2 mb = 2048000 byte(always in bytes)

    private String contentType;  //for ex: gpa, png , jpeg, mp3. GO to chrome: Common MIME Types

}
