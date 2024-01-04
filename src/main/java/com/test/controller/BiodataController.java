package com.test.controller;
import com.test.entity.Biodata;
import com.test.exception.CustomIllegalArgumentException;
import com.test.service.BiodataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.function.Supplier;

@RestController
@Validated
@RequestMapping("/user")  // Base mapping for the controller
public class BiodataController {
@Autowired
    private final BiodataService biodataService ;

    public BiodataController(BiodataService biodataService) {
        this.biodataService = biodataService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBiodata(@Validated @RequestBody Biodata biodata) {
        try {
            Biodata existingBiodata = biodataService.getBiodataByNpm(biodata.getNpm());

            BiodataStatus biodataStatus = checkExistingBiodata(existingBiodata);
            return switch (biodataStatus) {
                case ALREADY_EXISTS -> ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Peserta dengan NPM " + biodata.getNpm() + " sudah terdaftar");
                case DOES_NOT_EXIST -> {
                    Biodata savedBiodata = biodataService.saveBiodata(biodata);
                    yield ResponseEntity.status(HttpStatus.CREATED)
                            .body("Data dengan NPM " + savedBiodata.getNpm() + " berhasil ditambahkan");
                }
                default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("An unexpected error occurred.");
            };
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }


    private enum BiodataStatus {
        ALREADY_EXISTS,
        DOES_NOT_EXIST
    }

    private BiodataStatus checkExistingBiodata(Biodata existingBiodata) {
        return (existingBiodata != null) ? BiodataStatus.ALREADY_EXISTS : BiodataStatus.DOES_NOT_EXIST;
    }


    @GetMapping("/all")
    public ResponseEntity<Object> getUsers(
            @RequestHeader(name = "page", defaultValue = "0") int page,
            @RequestHeader(name = "size", defaultValue = "10") int size,
            @RequestHeader(name = "sort", defaultValue = "") String sort,
            @RequestHeader(name = "namalengkap", defaultValue = "") String namalengkap,
            @RequestHeader(name = "npm", defaultValue = "") String npm) {

        try {
            return switch (determineOperation(sort, namalengkap, npm)) {
                case "all" -> handleSortUsersResponse(() -> biodataService.getBiodata(page, size), "All users");
                case "sortAndSearchByName" -> handleSortAndSearchResponse(() -> biodataService.sortAndSearchUsersByName(page, size, getOrder(sort), namalengkap), "Sorted and searched users by name");
                case "sortAndSearchByNPM" -> handleSortAndSearchResponse(() -> biodataService.sortAndSearchUsersByNPM(page, size, getOrder(sort), npm), "Sorted and searched users by NPM");
                case "sort" -> handleSortUsersResponse(() -> biodataService.sortUsersByName(page, size, getOrder(sort)), "Sorted users");
                case "searchByName" -> handleSearchUserResponse(() -> biodataService.searchUserByName(namalengkap));
                default -> new ResponseEntity<>("Invalid operation", HttpStatus.BAD_REQUEST);
            };
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    // Fungsi untuk menentukan operasi berdasarkan parameter yang diberikan
    private String determineOperation(String sort, String namalengkap, String npm) {
        return switch (sort.isEmpty() ? 0 : 1) {
            case 0 -> namalengkap.isEmpty() ? "all" : npm.isEmpty() ? "searchByName" : "sortAndSearchByName";
            case 1 -> npm.isEmpty() ? "sort" : namalengkap.isEmpty() ? "sortAndSearchByNPM" : "invalid";
            default -> "invalid";
        };
    }


    // Fungsi untuk mendapatkan order berdasarkan string "asc" atau "desc"
    private Sort.Order getOrder(String sort) {
        return switch (sort.toLowerCase()) {
            case "asc" -> Sort.Order.asc("namalengkap");
            case "desc" -> Sort.Order.desc("namalengkap");
            default -> null;
        };
    }


private ResponseEntity<Object> handleResponse(Supplier<?> action, String message, String responseType) {
    try {
        Object result = action.get();
        return switch (responseType) {
            case "Page" -> {
                Page<?> pageResult = (Page<?>) result;
                yield pageResult.isEmpty()
                        ? new ResponseEntity<>("No users found for " + message, HttpStatus.NOT_FOUND)
                        : ResponseEntity.ok(pageResult);
            }
            case "List" -> {
                List<?> listResult = (List<?>) result;
                yield listResult.isEmpty()
                        ? new ResponseEntity<>("No users found for " + message, HttpStatus.NOT_FOUND)
                        : new ResponseEntity<>(listResult, HttpStatus.OK);
            }
            default -> new ResponseEntity<>("Invalid response type", HttpStatus.BAD_REQUEST);
        };
    } catch (Exception e) {
        return new ResponseEntity<>("Error handling response: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    private ResponseEntity<Object> handleSortUsersResponse(Supplier<Page<Biodata>> action, String message) {
        return handleResponse(action, message, "Page");
    }

    private ResponseEntity<Object> handleSortAndSearchResponse(Supplier<Page<Biodata>> action, String message) {
        return handleResponse(action, message, "Page");
    }

    private ResponseEntity<Object> handleSearchUserResponse(Supplier<List<Biodata>> action) {
        return handleResponse(action, "Searched users by name", "List");
    }

//


    @PutMapping("/updates")
    public ResponseEntity<String> updateBiodata(@Validated @RequestBody Biodata biodata) {
        try {
            Biodata updatedBiodata = biodataService.updateBiodata(biodata);
            return updatedBiodata != null ?
                    ResponseEntity.ok("Data dengan NPM : " + updatedBiodata.getNpm() + " berhasil diupdate") :
                    ResponseEntity.notFound().build();
        } catch (CustomIllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteBiodata(@PathVariable int id) {
        try {
            String result = biodataService.deleteBiodata(id);
            return ResponseEntity.ok(result);
        } catch (CustomIllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }
}
