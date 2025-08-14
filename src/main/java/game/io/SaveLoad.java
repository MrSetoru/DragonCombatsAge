package game.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.model.PlayerProfile;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class SaveLoad {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void save(PlayerProfile profile, String path) {
        try (FileWriter fw = new FileWriter(path)) {
            GSON.toJson(profile, fw);
        } catch (Exception e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    public static Optional&lt;playerprofile&gt;

    tryLoad(String path) {
        try {
            if (!Files.exists(Path.of(path))) return Optional.empty();
            try (FileReader fr = new FileReader(path)) {
                PlayerProfile p = GSON.fromJson(fr, PlayerProfile.class);
                // пост-инициализация: могут быть нули у transient-полей
                if (p.hero == null) return Optional.empty();
                if (p.hero.abilities == null || p.hero.abilities.isEmpty()) {
                    // восстановить базовые умения, если сломанный сейв
                    p.hero.abilities = new java.util.ArrayList & lt;&gt;
                    (game.model.AbilityFactory.basicSet());
                }
                return Optional.of(p);
            }
        } catch (Exception e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
            return Optional.empty();
        }
    }
}