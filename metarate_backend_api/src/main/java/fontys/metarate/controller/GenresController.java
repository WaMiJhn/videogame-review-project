package fontys.metarate.controller;

import fontys.metarate.business.GetGenresUseCase;
import fontys.metarate.domain.genre.GetGenresResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genres")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class GenresController {
    private final GetGenresUseCase getGenresUseCase;

    @GetMapping
    public ResponseEntity<GetGenresResponse> getGenres() {
        return ResponseEntity.ok(getGenresUseCase.getGenres());
    }
}
