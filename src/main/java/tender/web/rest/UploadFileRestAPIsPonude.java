package tender.web.rest;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tender.service.ExcelFileServicesPonude;
import tender.utils.ExcelUtilsPonude;

@RestController
public class UploadFileRestAPIsPonude {

    @Autowired
    ExcelFileServicesPonude fileServices;

    @PostMapping("/api/upload")
    public Message uploadFileMulti(@RequestParam("files") MultipartFile[] uploadfiles) {
        // Get file name
        String uploadedFileName = Arrays
            .stream(uploadfiles)
            .map(MultipartFile::getOriginalFilename)
            .filter(x -> !StringUtils.isEmpty(x))
            .collect(Collectors.joining(" , "));

        if (StringUtils.isEmpty(uploadedFileName)) {
            return new Message(uploadedFileName, "please select a file!", "fail");
        }

        String notExcelFiles = Arrays
            .stream(uploadfiles)
            .filter(x -> !ExcelUtilsPonude.isExcelFile(x))
            .map(x -> x.getOriginalFilename())
            .collect(Collectors.joining(" , "));

        /*
         * if(!StringUtils.isEmpty(notExcelFiles)) { return new Message(notExcelFiles,
         * "Not Excel Files", "fail"); }
         */

        try {
            for (MultipartFile file : uploadfiles) {
                fileServices.store(file);
            }
            return new Message(uploadedFileName, "Upload Successfully", "ok");
        } catch (Exception e) {
            return new Message(uploadedFileName, e.getMessage(), "fail");
        }
    }
}
