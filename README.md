# match-v-multimethod

Inspired by [Jeanine Adkisson's talk at the 2014
Clojure/Conj](https://www.youtube.com/watch?v=ZQkIWWTygio), this is a Clojure
application to test the performance of multimethod dispatch on [:variants ...]
vs. core.match dispatch on the same.

## Usage
```clojure
(require 'match-v-multimethod)
(in-ns 'match-v-multimethod)
(fight! 100000)
```

Or:
```bash
$ lein run 100000
```

## My Results

I first tested dispatching on [:a ...] through [:z ...], so there were
26 defmethods and 26 core.match possibilities. Here's what happened:
```clojure
match-v-multimethod=> (fight! 1000)
:n 1000
:multimethod
"Elapsed time: 2.432726 msecs"
:core.match
"Elapsed time: 3.186914 msecs"
nil
match-v-multimethod=> (fight! 10000)
:n 10000
:multimethod
"Elapsed time: 22.935646 msecs"
:core.match
"Elapsed time: 32.3315 msecs"
nil
match-v-multimethod=> (fight! 100000)
:n 100000
:multimethod
"Elapsed time: 25.294832 msecs"
:core.match
"Elapsed time: 103.567409 msecs"
nil
match-v-multimethod=> (fight! 1000000)
:n 1000000
:multimethod
"Elapsed time: 274.114439 msecs"
:core.match
"Elapsed time: 1064.427429 msecs"
nil
```

Then I bumped the number of variants up to 1000 by generating 1000
random keywords, and here's what happened:
```clojure
match-v-multimethod=> (fight! 1000)
:n 1000
:multimethod
"Elapsed time: 2.40198 msecs"
:core.match
"Elapsed time: 64.957017 msecs"
nil
match-v-multimethod=> (fight! 10000)
:n 10000
:multimethod
"Elapsed time: 18.108307 msecs"
:core.match
"Elapsed time: 492.554663 msecs"
nil
match-v-multimethod=> (fight! 100000)
:n 100000
:multimethod
"Elapsed time: 33.860444 msecs"
:core.match
"Elapsed time: 4623.31221 msecs"
nil
match-v-multimethod=> (fight! 1000000)
:n 1000000
:multimethod
"Elapsed time: 339.239158 msecs"
:core.match
"Elapsed time: 46426.171872 msecs"
nil
```

## License

Copyright © 2014 Matt Oquist <moquist@majen.net>

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
