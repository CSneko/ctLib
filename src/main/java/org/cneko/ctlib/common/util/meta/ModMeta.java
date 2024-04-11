package org.cneko.ctlib.common.util.meta;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.util.logging.Logger;

public interface ModMeta {
    File getDataFolder();
    Logger getLogger();
    @NotNull
    Description getDescription();
    @NotNull
    Server getServer();

    interface Description{
        @Nullable
        String getName();
        @Nullable
        String getVersion();
        @Nullable
        String getDescription();
        @Nullable
        String getWebsite();
        @Nullable
        Object[] getAuthors();

    }
    interface Server{
        boolean isOnlineMode();
        @Nullable
        String getVersion();
        @Nullable
        String getName();
        @Nullable
        String getMotd();
        int getPlayerAmount();
    }
}
