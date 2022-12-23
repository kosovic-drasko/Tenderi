package tender.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tender.domain.Specifikacije;
import tender.repository.SpecifikacijeRepository;
import tender.utils.ExcelUtilsSpecifikacije;

@Service
public class ExcelFileServicesSpecifikacije {

    @Autowired
    SpecifikacijeRepository specifikacijeRepository;

    // Store File Data to Database
    public void store(MultipartFile file) {
        try {
            List<Specifikacije> lstSpecifikacije = ExcelUtilsSpecifikacije.parseExcelFile(file.getInputStream());
            // Save Customers to DataBase
            specifikacijeRepository.saveAll(lstSpecifikacije);
        } catch (IOException e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }

    // Load Data to Excel File
    public ByteArrayInputStream loadFile() {
        List<Specifikacije> specifikacije = (List<Specifikacije>) specifikacijeRepository.findAll();

        try {
            ByteArrayInputStream in = ExcelUtilsSpecifikacije.customersToExcel(specifikacije);
            return in;
        } catch (IOException e) {}

        return null;
    }

    public ByteArrayInputStream loadObrazacPostupak(Integer sifraPostupka) {
        List<Specifikacije> specifikacije = specifikacijeRepository.findBySifraPostupka(sifraPostupka);

        try {
            ByteArrayInputStream in = ExcelUtilsSpecifikacije.customersToExcel(specifikacije);
            return in;
        } catch (IOException e) {}

        return null;
    }
}
