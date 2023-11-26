package fontys.metarate.controller;

import fontys.metarate.business.*;
import fontys.metarate.configuration.security.isauthenticated.IsAuthenticated;
import fontys.metarate.domain.game.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class GamesController {
    private final CreateGameUseCase createGameUseCase;
    private final GetGamesUseCase getGamesUseCase;
    private final UpdateGameUseCase updateGameUseCase;
    private final DeleteGameUseCase deleteGameUseCase;
    private final GetGameUseCase getGameUseCase;
    private final GetAverageGameRatingUseCase getAverageGameRatingUseCase;
    private final GetGamesWithHighRatingUseCase getGamesWithHighRatingUseCase;
    private final GetCountGamesUseCase getCountGamesUseCase;

    @GetMapping
    public ResponseEntity<GetAllGamesResponse> getAllGames(@RequestParam(required = false) String genre,
                                                           @RequestParam(required = false) String title,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "50") int size){
        GetAllGamesRequest request = GetAllGamesRequest.builder()
                .genreName(genre)
                .title(title)
                .page(page)
                .size(size)
                .build();
        GetAllGamesResponse response = getGamesUseCase.getGames(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{gameId}")
    public ResponseEntity<Game> getGame(@PathVariable(value = "gameId") final long id) {
        final Optional<Game> gameOptional = getGameUseCase.getGame(id);
        return gameOptional.map(game -> ResponseEntity.ok().body(game)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @PostMapping()
    public ResponseEntity<CreateGameResponse> createGame(@RequestBody @Valid CreateGameRequest request) {
        CreateGameResponse response = createGameUseCase.createGame(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @Transactional
    @PutMapping("{gameId}")
    public ResponseEntity<Void> updateGame(@PathVariable("gameId") long gameId,
                                           @RequestBody @Valid UpdateGameRequest request) {
        request.setId(gameId);
        updateGameUseCase.updateGame(request);
        return ResponseEntity.noContent().build();
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping("{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable long gameId) {
        deleteGameUseCase.deleteGame(gameId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{gameId}/average-rating")
    public Double getAverageRating(@PathVariable("gameId") Long gameId) {
        return getAverageGameRatingUseCase.getAverageRatingByGameId(gameId);
    }
    @GetMapping("/top-rated")
    public ResponseEntity<GetAllGamesResponse> getGamesWithHighRating(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        GetGamesWithHighRatingRequest request = GetGamesWithHighRatingRequest.builder()
                .page(page)
                .size(size)
                .build();
        GetAllGamesResponse response = getGamesWithHighRatingUseCase.getGamesWithHighRating(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/count")
    public Long getCountGames(@RequestParam(required = false) String genre, @RequestParam(required = false) String title) {
        GetCountGamesRequest request = GetCountGamesRequest.builder()
                .genre(genre)
                .title(title)
                .build();
        return getCountGamesUseCase.getCountGames(request);
    }
}
