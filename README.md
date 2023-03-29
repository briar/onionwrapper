## Onion Wrapper

A Java library providing a wrapper for running a Tor client on Android, Unix and Windows. The
wrapper can be used for running hidden services.

The library supports the `obfs4`, `meek_lite` and `snowflake` pluggable transports.

Binaries for Tor and pluggable transports are not included. They can be found in the following
Maven artifacts: `org.briarproject:{tor,obfs4proxy,snowflake}-{android,linux,windows}`. The
`obfs4proxy` artifact provides `obfs4` and `meek_lite`.

On Android, the library uses
[dont-kill-me-lib](https://code.briarproject.org/briar/dont-kill-me-lib) to hold a wake lock
whenever Tor's network connection is enabled. The helper classes in `dont-kill-me-lib` can be used
to work around certain manufacturer-specific power management restrictions that would prevent Tor
from running in the background for long periods.
