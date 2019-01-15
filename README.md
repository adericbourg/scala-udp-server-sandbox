# UDP server using Scala

Considered options:

* [Plain Java socket](java-socket/): not so relevant.
* [Akka](akka-io/): example working. Not easy to debug because of kind of losing
  stacktraces when using actors on the JVM?
* [Netty](netty/): example working.
* [fs2](/fs2/): not yet a working example.

## Testing

```plain-text
netcat localhost <port> -u -v
```
