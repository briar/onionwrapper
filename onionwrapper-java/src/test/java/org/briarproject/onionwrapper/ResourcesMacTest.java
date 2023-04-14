package org.briarproject.onionwrapper;

import org.junit.Before;
import org.junit.Test;

import static org.briarproject.onionwrapper.util.OsUtils.isMac;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeTrue;

public class ResourcesMacTest {

	@Before
	public void setUp() {
		assumeTrue(isMac());
	}

	@Test
	public void testCanLoadTor() {
		testCanLoadResource("any/tor");
	}

	@Test
	public void testCanLoadLibEvent() {
		testCanLoadResource("any/libevent-2.1.7.dylib");
	}

	@Test
	public void testCanLoadObfs4() {
		testCanLoadResource("any/obfs4proxy");
	}

	@Test
	public void testCanLoadSnowflake() {
		testCanLoadResource("any/snowflake");
	}

	private void testCanLoadResource(String name) {
		ClassLoader classLoader =
				Thread.currentThread().getContextClassLoader();
		assertNotNull(classLoader.getResourceAsStream(name));
	}
}
