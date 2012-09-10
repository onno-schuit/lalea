# lalea

An experimental website written in noir (a web app platform in Clojure). 

## Install & Run

If you use cake, substitute 'lein' with 'cake' below. Everything should work fine.

```bash
lein deps
lein run
```

## Technical Scope

In this project we're experimenting with Clojure and Noir, so all client-side 
stuff (including layout) is limited to a bare minimum.

We're focussing on:

* Clojure composition basics (how to use namespaces, load config files, etc)
* Architecture (Models, Views, as used by Noir)
* Server side validation
* Database abstraction layer (Korma, for now)
* Basic login system


## Not in Scope

i18n - there are some wrappers around the apparently excellent Java i18n: https://github.com/ptaoussanis/tower


## Domain

The website is a language learning tool. You make a list of words or phrases in
your native language and for each item you also provide the translation in the 
foreign language you're learning.

The website then shuffles the list and displays each item in turn. You type in 
the translation and get two retries if you make a mistake. At the end of the 
turn, the correct translation is displayed if you failed, otherwise you proceed 
to the next item.


## Flow

1. Collect all word - meaning pairs for a game
2. present pair
3. check answer:
   - correct: next round
   - incorrect: 
      - store id of the word
      - show pair again (twice, if necessary) -- use session object to store ids of wrong words
        (multiple ids of the same word, if necessary)
4. Next round
   - No more words: end
   - Other words: go to 2
5. End: show results



## License

Copyright Solin (C) 2012



