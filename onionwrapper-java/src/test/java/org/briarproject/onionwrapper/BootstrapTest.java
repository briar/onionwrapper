package org.briarproject.onionwrapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.logging.Logger.getLogger;
import static org.briarproject.nullsafety.NullSafety.requireNonNull;
import static org.briarproject.onionwrapper.TestUtils.deleteTestDirectory;
import static org.briarproject.onionwrapper.TestUtils.getArchitectureForTorBinary;
import static org.briarproject.onionwrapper.TestUtils.getTestDirectory;
import static org.briarproject.onionwrapper.TestUtils.isLinux;
import static org.briarproject.onionwrapper.TestUtils.isWindows;
import static org.briarproject.onionwrapper.TorWrapper.TorState.CONNECTED;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

public class BootstrapTest extends BaseTest {

	private final static Logger LOG = getLogger(BootstrapTest.class.getName());

	private static final int SOCKS_PORT = 59060;
	private static final int CONTROL_PORT = 59061;
	private final static long TIMEOUT = MINUTES.toMillis(2);

	private final ExecutorService executor = newCachedThreadPool();
	private final File torDir = getTestDirectory();

	@Before
	public void setUp() {
		assumeTrue(isLinux() || isWindows());
		assumeNotNull(getArchitectureForTorBinary());
	}

	@After
	public void tearDown() {
		deleteTestDirectory(torDir);
		executor.shutdown();
	}

	@Test
	public void testBootstrapping() throws Exception {
		String architecture = requireNonNull(getArchitectureForTorBinary());
		TorWrapper tor;
		if (isLinux()) {
			tor = new UnixTorWrapper(executor, executor, architecture, torDir,
					CONTROL_PORT, SOCKS_PORT);
		} else if (isWindows()) {
			tor = new WindowsTorWrapper(executor, executor, architecture, torDir,
					CONTROL_PORT, SOCKS_PORT);
		} else {
			throw new AssertionError("Running on unsupported OS");
		}

		boolean connected;
		try {
			tor.start();
			tor.enableNetwork(true);
			long start = System.currentTimeMillis();
			while (System.currentTimeMillis() - start < TIMEOUT) {
				if (tor.getTorState() == CONNECTED) break;
				//noinspection BusyWait
				Thread.sleep(500);
			}
			connected = tor.getTorState() == CONNECTED;
			if (connected) LOG.info("Connected to Tor");
			else LOG.warning("Could not connect to Tor within timeout");
		} finally {
			tor.stop();
		}
		assertTrue(connected);
	}
}
