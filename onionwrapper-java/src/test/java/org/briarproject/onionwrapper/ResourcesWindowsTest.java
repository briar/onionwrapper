package org.briarproject.onionwrapper;

import org.junit.Before;
import org.junit.Test;

import static org.briarproject.onionwrapper.TestUtils.isWindows;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeTrue;

public class ResourcesWindowsTest {

	@Before
	public void setUp() {
		assumeTrue(isWindows());
	}

	@Test
	public void testCanLoadTor() {
		testCanLoadResource("x86_64/tor.exe");
	}

	@Test
	public void testCanLoadObfs4() {
		testCanLoadResource("x86_64/obfs4proxy.exe");
	}

	@Test
	public void testCanLoadSnowflake() {
		testCanLoadResource("x86_64/snowflake.exe");
	}

	private void testCanLoadResource(String name) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assertNotNull(classLoader.getResourceAsStream(name));
	}
}
