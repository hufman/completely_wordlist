# Completely with Word Lists

[![Build Status](https://travis-ci.org/hufman/completely_wordlist.svg?branch=master)](https://travis-ci.org/hufman/completely_wordlist)
[![Coverage Status](https://coveralls.io/repos/github/hufman/completely_wordlist/badge.svg?branch=master)](https://coveralls.io/github/hufman/completely_wordlist?branch=master)

The Android Open Source Project provides some "wordlist" files for the example LatinIME input method.
Not just a list of words, these [wordlist files](https://android.googlesource.com/platform/packages/inputmethods/LatinIME/+/master/dictionaries/sample.combined) also contain frequency data,
which can be used to generated improved word-completion suggestions.
This is an experiment to integrate the [Completely](https://github.com/fmmfonseca/completely) word completion engine with this word frequency data.