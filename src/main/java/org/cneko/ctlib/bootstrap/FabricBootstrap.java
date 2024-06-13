package org.cneko.ctlib.bootstrap;

import net.fabricmc.api.ModInitializer;

public class FabricBootstrap implements ModInitializer {
    @Override
    public void onInitialize() {
        ModBootstrap.main(new String[]{});
    }
}
