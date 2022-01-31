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


    LOGGER.info("  .88888.                               dP   oo                   ");
    LOGGER.info(" d8'   `88                              88                        ");
    LOGGER.info(" 88        88d888b. .d8888b. .d8888b. d8888P dP 88d888b. .d8888b. ");
    LOGGER.info(" 88   YP88 88'  `88 88ooood8 88ooood8   88   88 88'  `88 88'  `88 ");
    LOGGER.info(" Y8.   .88 88       88.  ... 88.  ...   88   88 88    88 88.  .88 ");
    LOGGER.info("  `88888'  dP       `88888P' `88888P'   dP   dP dP    dP `8888P88 ");
    LOGGER.info("                                                              .88 ");
    LOGGER.info("                                                          d8888P");
    LOGGER.info("");
    LOGGER.infof("The account API is starting with profile: %s", ProfileManager.getActiveProfile());
    LOGGER.info("");
  }

  void onStop(@Observes ShutdownEvent ev) {
      LOGGER.info("The MicroService Account is stopping...");
  }
}
