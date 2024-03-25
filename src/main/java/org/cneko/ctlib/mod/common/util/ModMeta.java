package org.cneko.ctlib.mod.common.util;

import java.io.File;
import java.util.logging.Logger;

public abstract class ModMeta {
    public abstract File getDataFolder();
    public abstract Logger getLogger();
    public abstract int getPlayerAmount();
    public abstract Description getDescription();
    public abstract ServerInfo getServerInfo();

    public abstract class Description{
        public abstract String getName();
        public abstract String getVersion();
    }
    public abstract class ServerInfo{
        public abstract boolean getOnlineMode();
        public abstract String getVersion();
        public abstract String getName();
    }
}
