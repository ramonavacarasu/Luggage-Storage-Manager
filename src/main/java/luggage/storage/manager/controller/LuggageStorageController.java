package luggage.storage.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import luggage.storage.manager.entities.ReleasedLuggage;
import luggage.storage.manager.entities.StoredLuggage;
import luggage.storage.manager.services.ReleasedLuggageServiceImpl;
import luggage.storage.manager.services.StoredLuggageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LuggageStorageController {

    static final long STORAGE_CAPACITY = 100;

    private final StoredLuggageServiceImpl storedLuggageService;
    private final ReleasedLuggageServiceImpl releasedLuggageService;

    @Autowired
    public LuggageStorageController(StoredLuggageServiceImpl storedLuggageService,
                                    ReleasedLuggageServiceImpl releasedLuggageService) {
        this.storedLuggageService = storedLuggageService;
        this.releasedLuggageService = releasedLuggageService;
    }

    @GetMapping(value = "/storageCapacity")
    public long storageCapacity() {
        return STORAGE_CAPACITY;
    }

    @GetMapping(value = "/occupancy")
    public long occupancy() {
        return STORAGE_CAPACITY - storedLuggageService.count();
    }

    @PostMapping(value = "/saveLuggage")
    public StoredLuggage saveLuggage(StoredLuggage storedLuggage) {
        Instant start = Instant.now();

        if (storedLuggageService.count() < STORAGE_CAPACITY) {
            String code = codeValidator();

            storedLuggage.setLuggageID(code);
            storedLuggage.setStartTime(start);

            storedLuggageService.saveStoredLuggage(storedLuggage);
            return storedLuggage;
        }
        return null;
    }

    @PostMapping(value="/pickup")
    public ReleasedLuggage getMyLuggage(@RequestParam String code) {
        StoredLuggage storedLuggage = storedLuggageService.findByLuggageID(code);
        ReleasedLuggage releasedLuggage = new ReleasedLuggage();

        try {
            Instant start = storedLuggage.getStartTime();
            Duration duration = timeCalculator(start);
            long durationInMinutes = duration.toMinutes();

            releasedLuggage.setLuggageID(code);
            releasedLuggage.setDuration(durationInMinutes);
            releasedLuggage.setPrice(priceCalculator(durationInMinutes));
            releasedLuggageService.saveReleasedLuggage(releasedLuggage);
        } catch (NullPointerException ignored) { }

        return releasedLuggage;
    }

    @PostMapping(value="/release")
    public void releaseLuggage(@RequestParam String code) {
        storedLuggageService.deleteStoredLuggage(code);
    }

    @GetMapping("/luggage/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam long id) throws Exception {
        StoredLuggage storedLuggage = storedLuggageService.findByID(id);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString("This is your luggage code: " + storedLuggage.getLuggageID());
        byte[] text = json.substring(1, json.length() - 1).getBytes();

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(text.length);
        respHeaders.setContentType(new MediaType("text", "json"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "luggage.json");

        return new ResponseEntity<>(text, respHeaders, HttpStatus.OK);
    }

    private Duration timeCalculator(Instant start) {
        Instant end = Instant.now();
        return Duration.between(start, end);
    }

    private String codeValidator() {
        boolean flag = true;
        String finalCode = "";

        while (flag) {
            String tempCode = codeGenerator();

            if (storedLuggageService.luggageIDList().isEmpty()) {
                flag = false;
                finalCode = tempCode;
            } else {
                for (String str : storedLuggageService.luggageIDList()) {
                    if (tempCode.equals(str)) {
                        flag = true;
                        break;
                    } else {
                        flag = false;
                        finalCode = tempCode;
                    }
                }
            }
        }
        return finalCode;
    }

    private String codeGenerator() {
        Random random = new Random();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int x = random.nextInt(10);
            sb.append(x);
        } return sb.toString();
    }

    private long priceCalculator(long duration) {
        long totalPrice = 10;

        if (duration > 60) {
            double diff = duration - 60;
            double nextHours = diff / 60;
            double price = 5 * Math.ceil(nextHours);

            totalPrice += price;
        }
        return totalPrice;
    }

}
