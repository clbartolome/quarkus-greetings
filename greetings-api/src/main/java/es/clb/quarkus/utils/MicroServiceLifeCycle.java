package es.clb.quarkus.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.jboss.logging.Logger;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;

@ApplicationScoped
class MicroServiceLifeCycle {

  private static final Logger LOGGER = Logger.getLogger(MicroServiceLifeCycle.class);
  
  void onStart(@Observes StartupEvent ev) {
    LOGGER.info("");


    LOGGER.info(" $$$$$$\\                                 $$\\     $$\\                               ");
    LOGGER.info("$$  __$$\\                                $$ |    \\__|                              ");
    LOGGER.info("$$ /  \\__| $$$$$$\\   $$$$$$\\   $$$$$$\\ $$$$$$\\   $$\\ $$$$$$$\\   $$$$$$\\   $$$$$$$\\ ");
    LOGGER.info("$$ |$$$$\\ $$  __$$\\ $$  __$$\\ $$  __$$\\\\_$$  _|  $$ |$$  __$$\\ $$  __$$\\ $$  _____|");
    LOGGER.info("$$ |\\_$$ |$$ |  \\__|$$$$$$$$ |$$$$$$$$ | $$ |    $$ |$$ |  $$ |$$ /  $$ |\\$$$$$$\\  ");
    LOGGER.info("$$ |  $$ |$$ |      $$   ____|$$   ____| $$ |$$\\ $$ |$$ |  $$ |$$ |  $$ | \\____$$\\ ");
    LOGGER.info("\\$$$$$$  |$$ |      \\$$$$$$$\\ \\$$$$$$$\\  \\$$$$  |$$ |$$ |  $$ |\\$$$$$$$ |$$$$$$$  |");
    LOGGER.info(" \\______/ \\__|       \\_______| \\_______|  \\____/ \\__|\\__|  \\__| \\____$$ |\\_______/ ");
    LOGGER.info("                                                               $$\\   $$ |          ");
    LOGGER.info("                                                               \\$$$$$$  |          ");
    LOGGER.info("                                                                \\______/");

    LOGGER.infof("The Greetings API is starting with profile: %s", ProfileManager.getActiveProfile());
    LOGGER.info("");
  }

  void onStop(@Observes ShutdownEvent ev) {
      LOGGER.info("The Greetings API is stopping...");
  }
}
