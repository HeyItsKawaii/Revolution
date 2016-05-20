package org.mdev.revolution;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.mycila.guice.ext.closeable.CloseableModule;
import com.mycila.guice.ext.jsr250.Jsr250Module;
import com.netflix.governator.configuration.ArchaiusConfigurationProvider;
import com.netflix.governator.guice.LifecycleInjector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mdev.revolution.communication.encryption.HabboEncryption;
import org.mdev.revolution.communication.packets.PacketManager;
import org.mdev.revolution.database.DatabaseManager;
import org.mdev.revolution.network.Server;
import org.mdev.revolution.network.sessions.SessionManager;
import org.mdev.revolution.utilities.Configuration;

import java.io.File;

@Singleton
public class Revolution {
    private static final Logger logger = LogManager.getLogger(Revolution.class);

    public static final String SWF_RELEASE = "PRODUCTION-201601012205-226667486";
    public static final String N = "85F5D787695551F8899858DD52C1C19D080308C78C13FD9FBDCF6B8852B6FEF8A9380F5DEB39CC64F65321F94FE415F02E455A9A82B7E55AC3E9DF684347AA04A4A95B798A9C465042CB8EC95C91F0B68415E1A8CD9BCE1473D1397319295E0C7AA362E25992D83289FD4E2DAB39F794D4D779671DF18A898BFDCFE25CD0A5F1";
    public static final String D = "594E8FAF9B8E36A5B1103B3E372BD668B00205DA5D62A9152934F25AE1CF54A5C6255F93F22688434EE216A63542B94AC98391BC57254391D7F13F9AD7851C021703B238CD44EE121992AD950C020B899764A5FDDF9F09D459887AAA26BAAC08450FA6490243CAE1D7E69F372B6CAFE4C5BA0FBC095C9537E33EA795E6A848A3";
    public static final String E = "3";

    private static Injector injector;
    private static Configuration config;
    private static Revolution instance;

    public static Revolution getInstance() {
        if (instance == null) {
            synchronized (Revolution.class) {
                if (instance == null) {
                    instance = injector.getInstance(Revolution.class);
                }
            }
        }
        return instance;
    }

    public static Configuration getConfig() {
        return config;
    }

    private Server server;
    private DatabaseManager databaseManager;
    private SessionManager sessionManager;
    private PacketManager packetManager;

    private static void loadConfiguration(String configFile) {
        if (!new File(configFile).exists()) {
            logger.error("Unable to find the configuration file.");
            System.exit(0); // Fatal error!
        }
        config = new Configuration(configFile);
        logger.info("Configuration successfully loaded " + getConfig().size() + " properties.");
    }

    private static void loadEverythingElse() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        injector = getBootstrappedInjector();
        getInstance().databaseManager = new DatabaseManager();
        getInstance().databaseManager.initialize();

        getInstance().packetManager = new PacketManager();
        getInstance().packetManager.initialize();

        HabboEncryption.initialize(N, E, D);

        getInstance().sessionManager = new SessionManager();

        getInstance().getServer().start();
        Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
    }

    public Server getServer() {
        if (server == null) {
            synchronized (Server.class) {
                if (server == null) {
                    server = injector.getInstance(Server.class);
                }
            }
        }
        return server;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public PacketManager getPacketManager() {
        return packetManager;
    }

    public static Injector getBootstrappedInjector() {
        return LifecycleInjector
                .builder()
                .usingBasePackages("org.mdev.revolution")
                .withBootstrapModule(
                        binder -> binder.bindConfigurationProvider().toInstance(ArchaiusConfigurationProvider.builder().build())
                )
                .withModuleClass(CloseableModule.class)
                .withModuleClass(Jsr250Module.class)
                .build().createInjector();
    }

    public static void main(String[] args) {
        String configFile = "./config/default.cfg.properties";

        if (args.length > 0) {
            switch (args[0].toLowerCase().trim()) {
                case "c":
                    configFile = args[1];
                    break;
                default:
                    System.out.println("Unsupported command-line argument, please make sure you've spelled correctly as everything is case-sensitive.");
                    System.out.println("Press any key to quit.");
                    System.exit(0);
                    break;
            }
        }

        long start = System.currentTimeMillis();
        loadConfiguration(configFile);
        loadEverythingElse();
        //server.loadAssets();

        long elapsed = System.currentTimeMillis() - start;
        logger.info("Revolution Server started up in " + elapsed + "ms");

        Thread hook = new Thread(Revolution::shutdown);
        Runtime.getRuntime().addShutdownHook(hook);
    }

    private static void shutdown() {
        Revolution.getInstance().getServer().stop();
        Revolution.getInstance().getDatabaseManager().dispose();
        Revolution.getInstance().getPacketManager().dispose();
    }
}