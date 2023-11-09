package io.github.footaku.erai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.github.footaku.erai.configuration.Nullability;
import io.github.footaku.erai.configuration.Setting;

import java.nio.file.Path;
import java.util.List;

/**
 * Extra Rules of Architecture Inspection
 */
public class Erai {
    private static Setting setting;

    private Erai() {
    }

    /**
     * Get setting.
     *
     * @return setting
     */
    public static synchronized Setting getSetting() {
        if (setting == null) {
            var mapper = new ObjectMapper(new YAMLFactory());
            try {
                // TODO fallback another yaml file name
                var path = Path.of("erai.yaml");
                setting = mapper.readValue(path.toFile(), Setting.class);
            } catch (Exception e) {
                // TODO Logger
                setting = new Setting(
                    new Nullability(
                        new Nullability.ReturnValue(List.of(), List.of())
                    )
                );
            }
        }
        return setting;
    }
}
