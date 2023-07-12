package io.github.footaku.erai;

import com.societegenerale.commons.plugin.model.RootClassFolder;
import com.societegenerale.commons.plugin.service.ScopePathProvider;

public class TestScopeProvider implements ScopePathProvider {
    @Override
    public RootClassFolder getMainClassesPath() {
        return new RootClassFolder("./build/classes/java/test/");
    }

    @Override
    public RootClassFolder getTestClassesPath() {
        return new RootClassFolder("./target");
    }
}
