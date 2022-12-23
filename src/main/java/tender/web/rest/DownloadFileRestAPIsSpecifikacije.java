package tender.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tender.service.ExcelFileServicesSpecifikacije;

@RestController
public class DownloadFileRestAPIsSpecifikacije {

    @Autowired
    ExcelFileServicesSpecifikacije fileServices;

    /*
     * Download Files
     */
    @GetMapping("/api/specifikacije/file")
    public ResponseEntity<InputStreamResource> downloadFile() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=specifikacije.xlsx");

        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            .body(new InputStreamResource(fileServices.loadFile()));
    }

    @GetMapping("/api/specifikacije/file/{sifraPostupka}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("sifraPostupka") Integer sifraPostupka) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=specifikacije.xlsx");

        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            .body(new InputStreamResource(fileServices.loadObrazacPostupak(sifraPostupka)));
    }
}
