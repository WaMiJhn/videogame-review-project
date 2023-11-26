package fontys.metarate.configuration.db;

import fontys.metarate.persistence.GameRepository;
import fontys.metarate.persistence.GenreRepository;
import fontys.metarate.persistence.ReviewRepository;
import fontys.metarate.persistence.UserRepository;
import fontys.metarate.persistence.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Component
public class DatabaseDummyDataInitializer {

    private final GenreRepository genreRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final GameRepository gameRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void populateDatabaseInitialDummyData() {
        if (isDatabaseEmpty()) {
            insertGenres();
            insertAdminUser();
            insertNormalUser();
            insertGames();
            insertReviews();
        }
    }

    private boolean isDatabaseEmpty() {
        return genreRepository.count() == 0;
    }

    private void insertGenres() {
        List<GenreEntity> genres = Arrays.asList(
                GenreEntity.builder().name("Action").build(),
                GenreEntity.builder().name("Adventure").build(),
                GenreEntity.builder().name("RPG").build(),
                GenreEntity.builder().name("Simulation").build(),
                GenreEntity.builder().name("Strategy").build(),
                GenreEntity.builder().name("Sports").build(),
                GenreEntity.builder().name("Racing").build(),
                GenreEntity.builder().name("Fighting").build(),
                GenreEntity.builder().name("Platformer").build(),
                GenreEntity.builder().name("Puzzle").build()
        );

        genreRepository.saveAll(genres);
    }

    private void insertAdminUser() {
        UserEntity adminUser = UserEntity.builder()
                .username("admin")
                .password(passwordEncoder.encode("123"))
                .profilePic("https://soccerpointeclaire.com/wp-content/uploads/2021/06/default-profile-pic-e1513291410505.jpg")
                .build();

        UserRoleEntity adminRole = UserRoleEntity.builder()
                .role(RoleEnum.ADMIN)
                .user(adminUser)
                .build();
        UserRoleEntity userRole = UserRoleEntity.builder()
                .role(RoleEnum.USER)
                .user(adminUser)
                .build();

        adminUser.setUserRoles(Set.of(adminRole, userRole));
        userRepository.save(adminUser);
    }

    private void insertNormalUser() {
        UserEntity user1 = UserEntity.builder()
                .username("user1")
                .password(passwordEncoder.encode("123"))
                .profilePic("https://soccerpointeclaire.com/wp-content/uploads/2021/06/default-profile-pic-e1513291410505.jpg")
                .build();

        UserEntity user2 = UserEntity.builder()
                .username("user2")
                .password(passwordEncoder.encode("123"))
                .profilePic("https://soccerpointeclaire.com/wp-content/uploads/2021/06/default-profile-pic-e1513291410505.jpg")
                .build();
        UserEntity user3 = UserEntity.builder()
                .username("user3")
                .password(passwordEncoder.encode("123"))
                .profilePic("https://soccerpointeclaire.com/wp-content/uploads/2021/06/default-profile-pic-e1513291410505.jpg")
                .build();

        UserEntity user4 = UserEntity.builder()
                .username("user4")
                .password(passwordEncoder.encode("123"))
                .profilePic("https://soccerpointeclaire.com/wp-content/uploads/2021/06/default-profile-pic-e1513291410505.jpg")
                .build();

        UserRoleEntity userRole1 = UserRoleEntity.builder()
                .role(RoleEnum.USER)
                .user(user1)
                .build();
        UserRoleEntity userRole2 = UserRoleEntity.builder()
                .role(RoleEnum.USER)
                .user(user2)
                .build();
        UserRoleEntity userRole3 = UserRoleEntity.builder()
                .role(RoleEnum.USER)
                .user(user3)
                .build();
        UserRoleEntity userRole4 = UserRoleEntity.builder()
                .role(RoleEnum.USER)
                .user(user4)
                .build();


        user1.setUserRoles(Set.of(userRole1));
        user2.setUserRoles(Set.of(userRole2));
        user3.setUserRoles(Set.of(userRole3));
        user4.setUserRoles(Set.of(userRole4));
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
    }
    private void insertGames() {
        GenreEntity strategyGenre = genreRepository.findByName("Strategy");
        GenreEntity actionGenre = genreRepository.findByName("Action");
        GenreEntity rpgGenre = genreRepository.findByName("RPG");
        GenreEntity platformerGenre = genreRepository.findByName("Platformer");

        GameEntity spiderman = GameEntity.builder()
                .title("Marvel's Spider-Man: Miles Morales")
                .developer("Playstation Studios")
                .genre(actionGenre)
                .releaseYear(2020)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tincidunt lacus sed elementum vehicula. Etiam laoreet vehicula ipsum, nec sagittis dui sollicitudin ut. Nulla volutpat accumsan orci, at hendrerit mauris tempus semper. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis ac arcu sed lectus accumsan viverra. Nunc iaculis sem et interdum accumsan. Aenean sed sagittis urna. Etiam vitae gravida arcu, ut accumsan elit.\n" +
                        "\n" +
                        "Maecenas accumsan commodo eleifend. Maecenas dictum, nulla quis aliquam dignissim, nulla massa volutpat sapien, sit amet consequat metus dolor id leo. Nam porttitor a metus tincidunt maximus. Proin imperdiet sagittis justo et laoreet. Nam imperdiet iaculis luctus. Pellentesque ut euismod risus. Nam nec metus a purus egestas bibendum. Suspendisse potenti. Aenean faucibus elit eget velit tincidunt, eu aliquam nibh tristique. Aliquam vitae tincidunt metus. Morbi non convallis arcu, non suscipit diam. Morbi tempus nulla consequat, vehicula erat ut, semper ante. Suspendisse egestas ligula nec libero imperdiet, at molestie risus congue. Aenean molestie magna a magna ultricies, sit amet posuere lorem tincidunt. Maecenas semper tortor eros, et imperdiet turpis lacinia sed.")
                .imageUrl("https://image.api.playstation.com/vulcan/ap/rnd/202008/1020/T45iRN1bhiWcJUzST6UFGBvO.png")
                .trailerUrl("https://www.youtube.com/embed/NTunTURbyUU")
                .build();

        GameEntity roblox = GameEntity.builder()
                .title("Roblox")
                .developer("Roblox Corporation")
                .genre(strategyGenre)
                .releaseYear(2020)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tincidunt lacus sed elementum vehicula. Etiam laoreet vehicula ipsum, nec sagittis dui sollicitudin ut. Nulla volutpat accumsan orci, at hendrerit mauris tempus semper. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis ac arcu sed lectus accumsan viverra. Nunc iaculis sem et interdum accumsan. Aenean sed sagittis urna. Etiam vitae gravida arcu, ut accumsan elit.\n" +
                        "\n" +
                        "Maecenas accumsan commodo eleifend. Maecenas dictum, nulla quis aliquam dignissim, nulla massa volutpat sapien, sit amet consequat metus dolor id leo. Nam porttitor a metus tincidunt maximus. Proin imperdiet sagittis justo et laoreet. Nam imperdiet iaculis luctus. Pellentesque ut euismod risus. Nam nec metus a purus egestas bibendum. Suspendisse potenti. Aenean faucibus elit eget velit tincidunt, eu aliquam nibh tristique. Aliquam vitae tincidunt metus. Morbi non convallis arcu, non suscipit diam. Morbi tempus nulla consequat, vehicula erat ut, semper ante. Suspendisse egestas ligula nec libero imperdiet, at molestie risus congue. Aenean molestie magna a magna ultricies, sit amet posuere lorem tincidunt. Maecenas semper tortor eros, et imperdiet turpis lacinia sed.")
                .imageUrl("https://kaartdirect.nl/storage/thumbnails/default/4r/rb/9ca3u4ys84ogosc0kscc.png?p=kaartdirect.nl%2Fimages%2Fblogs%2Fhaal-meer-uit-roblox-met-een-roblox-gift-card1625854387.png&s=https&widen=840")
                .trailerUrl("https://www.youtube.com/embed/eAvXhNlO-rA")
                .build();

        GameEntity clashOfClans = GameEntity.builder()
                .title("Clash of Clans")
                .developer("Supercell")
                .genre(strategyGenre)
                .releaseYear(2020)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tincidunt lacus sed elementum vehicula. Etiam laoreet vehicula ipsum, nec sagittis dui sollicitudin ut. Nulla volutpat accumsan orci, at hendrerit mauris tempus semper. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis ac arcu sed lectus accumsan viverra. Nunc iaculis sem et interdum accumsan. Aenean sed sagittis urna. Etiam vitae gravida arcu, ut accumsan elit.\n" +
                        "\n" +
                        "Maecenas accumsan commodo eleifend. Maecenas dictum, nulla quis aliquam dignissim, nulla massa volutpat sapien, sit amet consequat metus dolor id leo. Nam porttitor a metus tincidunt maximus. Proin imperdiet sagittis justo et laoreet. Nam imperdiet iaculis luctus. Pellentesque ut euismod risus. Nam nec metus a purus egestas bibendum. Suspendisse potenti. Aenean faucibus elit eget velit tincidunt, eu aliquam nibh tristique. Aliquam vitae tincidunt metus. Morbi non convallis arcu, non suscipit diam. Morbi tempus nulla consequat, vehicula erat ut, semper ante. Suspendisse egestas ligula nec libero imperdiet, at molestie risus congue. Aenean molestie magna a magna ultricies, sit amet posuere lorem tincidunt. Maecenas semper tortor eros, et imperdiet turpis lacinia sed.")
                .imageUrl("https://clashofclans.com/uploaded-images-blog/clash-of-clans-app-icon.jpg?mtime=20181025123934")
                .trailerUrl("https://www.youtube.com/embed/XH3Xu1-cvII")
                .build();

        GameEntity minecraft = GameEntity.builder()
                .title("Minecraft")
                .developer("Microsoft")
                .genre(rpgGenre)
                .releaseYear(2020)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tincidunt lacus sed elementum vehicula. Etiam laoreet vehicula ipsum, nec sagittis dui sollicitudin ut. Nulla volutpat accumsan orci, at hendrerit mauris tempus semper. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis ac arcu sed lectus accumsan viverra. Nunc iaculis sem et interdum accumsan. Aenean sed sagittis urna. Etiam vitae gravida arcu, ut accumsan elit.\n" +
                        "\n" +
                        "Maecenas accumsan commodo eleifend. Maecenas dictum, nulla quis aliquam dignissim, nulla massa volutpat sapien, sit amet consequat metus dolor id leo. Nam porttitor a metus tincidunt maximus. Proin imperdiet sagittis justo et laoreet. Nam imperdiet iaculis luctus. Pellentesque ut euismod risus. Nam nec metus a purus egestas bibendum. Suspendisse potenti. Aenean faucibus elit eget velit tincidunt, eu aliquam nibh tristique. Aliquam vitae tincidunt metus. Morbi non convallis arcu, non suscipit diam. Morbi tempus nulla consequat, vehicula erat ut, semper ante. Suspendisse egestas ligula nec libero imperdiet, at molestie risus congue. Aenean molestie magna a magna ultricies, sit amet posuere lorem tincidunt. Maecenas semper tortor eros, et imperdiet turpis lacinia sed.")
                .imageUrl("https://image.api.playstation.com/vulcan/img/cfn/11307x4B5WLoVoIUtdewG4uJ_YuDRTwBxQy0qP8ylgazLLc01PBxbsFG1pGOWmqhZsxnNkrU3GXbdXIowBAstzlrhtQ4LCI4.png")
                .trailerUrl("https://www.youtube.com/embed/MmB9b5njVbA")
                .build();
        GameEntity fortnite = GameEntity.builder()
                .title("Fortnite")
                .developer("Epic Games")
                .genre(actionGenre)
                .releaseYear(2018)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tincidunt lacus sed elementum vehicula. Etiam laoreet vehicula ipsum, nec sagittis dui sollicitudin ut. Nulla volutpat accumsan orci, at hendrerit mauris tempus semper. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis ac arcu sed lectus accumsan viverra. Nunc iaculis sem et interdum accumsan. Aenean sed sagittis urna. Etiam vitae gravida arcu, ut accumsan elit.\n" +
                        "\n" +
                        "Maecenas accumsan commodo eleifend. Maecenas dictum, nulla quis aliquam dignissim, nulla massa volutpat sapien, sit amet consequat metus dolor id leo. Nam porttitor a metus tincidunt maximus. Proin imperdiet sagittis justo et laoreet. Nam imperdiet iaculis luctus. Pellentesque ut euismod risus. Nam nec metus a purus egestas bibendum. Suspendisse potenti. Aenean faucibus elit eget velit tincidunt, eu aliquam nibh tristique. Aliquam vitae tincidunt metus. Morbi non convallis arcu, non suscipit diam. Morbi tempus nulla consequat, vehicula erat ut, semper ante. Suspendisse egestas ligula nec libero imperdiet, at molestie risus congue. Aenean molestie magna a magna ultricies, sit amet posuere lorem tincidunt. Maecenas semper tortor eros, et imperdiet turpis lacinia sed.")
                .imageUrl("https://img-prod-cms-rt-microsoft-com.akamaized.net/cms/api/am/imageFileData/RW10idH?ver=489b")
                .trailerUrl("https://www.youtube.com/embed/AzP22cDSo-A")
                .build();
        GameEntity subwaysurf = GameEntity.builder()
                .title("Subway Surfers")
                .developer("Kiloo")
                .genre(platformerGenre)
                .releaseYear(2012)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tincidunt lacus sed elementum vehicula. Etiam laoreet vehicula ipsum, nec sagittis dui sollicitudin ut. Nulla volutpat accumsan orci, at hendrerit mauris tempus semper. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis ac arcu sed lectus accumsan viverra. Nunc iaculis sem et interdum accumsan. Aenean sed sagittis urna. Etiam vitae gravida arcu, ut accumsan elit.\n" +
                        "\n" +
                        "Maecenas accumsan commodo eleifend. Maecenas dictum, nulla quis aliquam dignissim, nulla massa volutpat sapien, sit amet consequat metus dolor id leo. Nam porttitor a metus tincidunt maximus. Proin imperdiet sagittis justo et laoreet. Nam imperdiet iaculis luctus. Pellentesque ut euismod risus. Nam nec metus a purus egestas bibendum. Suspendisse potenti. Aenean faucibus elit eget velit tincidunt, eu aliquam nibh tristique. Aliquam vitae tincidunt metus. Morbi non convallis arcu, non suscipit diam. Morbi tempus nulla consequat, vehicula erat ut, semper ante. Suspendisse egestas ligula nec libero imperdiet, at molestie risus congue. Aenean molestie magna a magna ultricies, sit amet posuere lorem tincidunt. Maecenas semper tortor eros, et imperdiet turpis lacinia sed.")
                .imageUrl("https://assets-prd.ignimgs.com/2023/03/01/subwaysurfers-1677630205274.jpg")
                .trailerUrl("https://www.youtube.com/embed/fGy-x3H-nxU")
                .build();
        GameEntity clubpenguin = GameEntity.builder()
                .title("Club Penguin")
                .developer("RocketSnail Games")
                .genre(platformerGenre)
                .releaseYear(2005)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tincidunt lacus sed elementum vehicula. Etiam laoreet vehicula ipsum, nec sagittis dui sollicitudin ut. Nulla volutpat accumsan orci, at hendrerit mauris tempus semper. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis ac arcu sed lectus accumsan viverra. Nunc iaculis sem et interdum accumsan. Aenean sed sagittis urna. Etiam vitae gravida arcu, ut accumsan elit.\n" +
                        "\n" +
                        "Maecenas accumsan commodo eleifend. Maecenas dictum, nulla quis aliquam dignissim, nulla massa volutpat sapien, sit amet consequat metus dolor id leo. Nam porttitor a metus tincidunt maximus. Proin imperdiet sagittis justo et laoreet. Nam imperdiet iaculis luctus. Pellentesque ut euismod risus. Nam nec metus a purus egestas bibendum. Suspendisse potenti. Aenean faucibus elit eget velit tincidunt, eu aliquam nibh tristique. Aliquam vitae tincidunt metus. Morbi non convallis arcu, non suscipit diam. Morbi tempus nulla consequat, vehicula erat ut, semper ante. Suspendisse egestas ligula nec libero imperdiet, at molestie risus congue. Aenean molestie magna a magna ultricies, sit amet posuere lorem tincidunt. Maecenas semper tortor eros, et imperdiet turpis lacinia sed.")
                .imageUrl("https://globalnews.ca/wp-content/uploads/2018/09/clubpenguin.jpg?quality=85&strip=all")
                .trailerUrl("https://www.youtube.com/embed/tEbuKuNUwhQ")
                .build();
        GameEntity amongus = GameEntity.builder()
                .title("Among Us")
                .developer("InnerSloth LLC")
                .genre(strategyGenre)
                .releaseYear(2018)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tincidunt lacus sed elementum vehicula. Etiam laoreet vehicula ipsum, nec sagittis dui sollicitudin ut. Nulla volutpat accumsan orci, at hendrerit mauris tempus semper. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis ac arcu sed lectus accumsan viverra. Nunc iaculis sem et interdum accumsan. Aenean sed sagittis urna. Etiam vitae gravida arcu, ut accumsan elit.\n" +
                        "\n" +
                        "Maecenas accumsan commodo eleifend. Maecenas dictum, nulla quis aliquam dignissim, nulla massa volutpat sapien, sit amet consequat metus dolor id leo. Nam porttitor a metus tincidunt maximus. Proin imperdiet sagittis justo et laoreet. Nam imperdiet iaculis luctus. Pellentesque ut euismod risus. Nam nec metus a purus egestas bibendum. Suspendisse potenti. Aenean faucibus elit eget velit tincidunt, eu aliquam nibh tristique. Aliquam vitae tincidunt metus. Morbi non convallis arcu, non suscipit diam. Morbi tempus nulla consequat, vehicula erat ut, semper ante. Suspendisse egestas ligula nec libero imperdiet, at molestie risus congue. Aenean molestie magna a magna ultricies, sit amet posuere lorem tincidunt. Maecenas semper tortor eros, et imperdiet turpis lacinia sed.")
                .imageUrl("https://i0.wp.com/news.xbox.com/en-us/wp-content/uploads/sites/2/2021/12/Hero.jpg?resize=1200%2C675&ssl=1")
                .trailerUrl("https://www.youtube.com/embed/0YKjFoGxbec")
                .build();
        GameEntity eldenring = GameEntity.builder()
                .title("Elden Ring")
                .developer("From Software")
                .genre(rpgGenre)
                .releaseYear(2022)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tincidunt lacus sed elementum vehicula. Etiam laoreet vehicula ipsum, nec sagittis dui sollicitudin ut. Nulla volutpat accumsan orci, at hendrerit mauris tempus semper. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis ac arcu sed lectus accumsan viverra. Nunc iaculis sem et interdum accumsan. Aenean sed sagittis urna. Etiam vitae gravida arcu, ut accumsan elit.\n" +
                        "\n" +
                        "Maecenas accumsan commodo eleifend. Maecenas dictum, nulla quis aliquam dignissim, nulla massa volutpat sapien, sit amet consequat metus dolor id leo. Nam porttitor a metus tincidunt maximus. Proin imperdiet sagittis justo et laoreet. Nam imperdiet iaculis luctus. Pellentesque ut euismod risus. Nam nec metus a purus egestas bibendum. Suspendisse potenti. Aenean faucibus elit eget velit tincidunt, eu aliquam nibh tristique. Aliquam vitae tincidunt metus. Morbi non convallis arcu, non suscipit diam. Morbi tempus nulla consequat, vehicula erat ut, semper ante. Suspendisse egestas ligula nec libero imperdiet, at molestie risus congue. Aenean molestie magna a magna ultricies, sit amet posuere lorem tincidunt. Maecenas semper tortor eros, et imperdiet turpis lacinia sed.")
                .imageUrl("https://image.api.playstation.com/vulcan/ap/rnd/202107/1612/Y5RHNmzAtc6sRYwZlYiKHAxN.png")
                .trailerUrl("https://www.youtube.com/embed/E3Huy2cdih0")
                .build();
        GameEntity undertale = GameEntity.builder()
                .title("Undertale")
                .developer("tobyfox")
                .genre(rpgGenre)
                .releaseYear(2015)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tincidunt lacus sed elementum vehicula. Etiam laoreet vehicula ipsum, nec sagittis dui sollicitudin ut. Nulla volutpat accumsan orci, at hendrerit mauris tempus semper. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis ac arcu sed lectus accumsan viverra. Nunc iaculis sem et interdum accumsan. Aenean sed sagittis urna. Etiam vitae gravida arcu, ut accumsan elit.\n" +
                        "\n" +
                        "Maecenas accumsan commodo eleifend. Maecenas dictum, nulla quis aliquam dignissim, nulla massa volutpat sapien, sit amet consequat metus dolor id leo. Nam porttitor a metus tincidunt maximus. Proin imperdiet sagittis justo et laoreet. Nam imperdiet iaculis luctus. Pellentesque ut euismod risus. Nam nec metus a purus egestas bibendum. Suspendisse potenti. Aenean faucibus elit eget velit tincidunt, eu aliquam nibh tristique. Aliquam vitae tincidunt metus. Morbi non convallis arcu, non suscipit diam. Morbi tempus nulla consequat, vehicula erat ut, semper ante. Suspendisse egestas ligula nec libero imperdiet, at molestie risus congue. Aenean molestie magna a magna ultricies, sit amet posuere lorem tincidunt. Maecenas semper tortor eros, et imperdiet turpis lacinia sed.")
                .imageUrl("https://m.media-amazon.com/images/M/MV5BNzkwZDliNGEtMDNkNi00ODcxLWI1N2UtNDE1NmZlM2QyMTY4XkEyXkFqcGdeQXVyMTA0MTM5NjI2._V1_.jpg")
                .trailerUrl("https://www.youtube.com/embed/ycsnBIX8wTU")
                .build();

        gameRepository.saveAll(List.of(amongus, spiderman, eldenring, clubpenguin, fortnite, undertale, subwaysurf, clashOfClans, minecraft, roblox));
    }

    private void insertReviews() {
        GameEntity spiderman;
        List<GameEntity> games1 = gameRepository.findByTitleContainingIgnoreCase("spider");
        UserEntity user = userRepository.findByUsername("user1");
        if (!games1.isEmpty()) {
            spiderman = games1.get(0);
            ReviewEntity review = ReviewEntity.builder()
                    .game(spiderman)
                    .user(user)
                    .content("Lorem ipsum")
                    .date(LocalDateTime.now())
                    .rating(10)
                    .build();
            reviewRepository.save(review);
        }
        GameEntity amongus;
        List<GameEntity> games2 = gameRepository.findByTitleContainingIgnoreCase("among");
        if (!games2.isEmpty()) {
            amongus = games2.get(0);
            ReviewEntity review = ReviewEntity.builder()
                    .game(amongus)
                    .user(user)
                    .content("Lorem ipsum")
                    .date(LocalDateTime.now())
                    .rating(10)
                    .build();
            reviewRepository.save(review);
        }
        GameEntity elden;
        List<GameEntity> games3 = gameRepository.findByTitleContainingIgnoreCase("elden");
        if (!games3.isEmpty()) {
            elden = games3.get(0);
            ReviewEntity review = ReviewEntity.builder()
                    .game(elden)
                    .user(user)
                    .content("Lorem ipsum")
                    .date(LocalDateTime.now())
                    .rating(10)
                    .build();
            reviewRepository.save(review);
        }
        GameEntity fortnite;
        List<GameEntity> games4 = gameRepository.findByTitleContainingIgnoreCase("fortnite");
        if (!games4.isEmpty()) {
            fortnite = games4.get(0);
            ReviewEntity review = ReviewEntity.builder()
                    .game(fortnite)
                    .user(user)
                    .content("Lorem ipsum")
                    .date(LocalDateTime.now())
                    .rating(9)
                    .build();
            reviewRepository.save(review);
        }
        GameEntity club;
        List<GameEntity> games5 = gameRepository.findByTitleContainingIgnoreCase("club");
        if (!games5.isEmpty()) {
            club = games5.get(0);
            ReviewEntity review = ReviewEntity.builder()
                    .game(club)
                    .user(user)
                    .content("Lorem ipsum")
                    .date(LocalDateTime.now())
                    .rating(9)
                    .build();
            reviewRepository.save(review);
        }
        GameEntity roblox;
        List<GameEntity> games6 = gameRepository.findByTitleContainingIgnoreCase("roblox");
        if (!games6.isEmpty()) {
            roblox = games6.get(0);
            ReviewEntity review = ReviewEntity.builder()
                    .game(roblox)
                    .user(user)
                    .content("Lorem ipsum")
                    .date(LocalDateTime.now())
                    .rating(10)
                    .build();
            reviewRepository.save(review);
        }
    }

}
