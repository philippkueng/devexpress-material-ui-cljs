### Example Project

This repo is a fork of https://github.com/mhuebert/shadow-re-frame/ and extended with [Material UI](https://material-ui.com/) and [DevExpress Components](https://devexpress.github.io/devextreme-reactive/)

Usage of [re-frame](https://github.com/Day8/re-frame) and the [shadow-cljs](https://github.com/thheller/shadow-cljs/) build tool.

----

[shadow-cljs](https://github.com/thheller/shadow-cljs/) is a fairly new-to-the-world (but used by @thheller for some years already) ClojureScript build tool. It's improving day by day. It does some nice things, for example caching intermediate compile results, which can speed up `:advanced` builds by 5x or more (with a hot cache). It's also the only build tool that supports bundling of dependencies for the self-hosted compiler.

To get started:

```
git clone https://github.com/philippkueng/devexpress-material-ui-cljs.git ;
cd devexpress-material-ui-cljs;
yarn;
yarn run watch;
```

Then, open a browser window to http://localhost:8700.