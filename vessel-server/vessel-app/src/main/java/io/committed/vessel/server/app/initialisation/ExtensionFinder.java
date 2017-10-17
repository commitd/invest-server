package io.committed.vessel.server.app.initialisation;

import java.util.ArrayList;
import java.util.List;

import io.committed.vessel.extensions.VesselExtension;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import reactor.core.publisher.Flux;

public class ExtensionFinder {

  private static String[] BLACKLIST = {
      "-org.springframework.",
      "-java.",
      "-javax.",
      // TODO: We might need to construct this from the plugins/ dir? so that we whitelist the
      // ones there. Premature optimisation?
      // "-jar:"
  };
  private final boolean verbose;

  public ExtensionFinder(final boolean verbose) {
    this.verbose = verbose;
  }

  public Flux<Class<? extends VesselExtension>> find() {
    final List<Class<? extends VesselExtension>> extensions = new ArrayList<>();

    new FastClasspathScanner(BLACKLIST)
        .verbose(verbose)
        .matchClassesImplementing(VesselExtension.class, c -> extensions.add(c))
        .scan();

    return Flux.fromStream(extensions.stream());
  }
}
