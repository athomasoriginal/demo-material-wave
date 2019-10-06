# material-wave

## Quick Start

- Run App

  ```bash
  clj -A:dev
  ```

- Visit App

  http://localhost:9500


## Github Pages Deploy

- Build Prod

  ```bash
  clj -A:prod
  ```

Push to `master` and visit the site at https://tkjone.github.io/demo-material-wave/resources/public/

The way I have configured this build setup is a little lazy of me.  The idea is that we do not want to have to keep changing the location of our `*.js` files in our `*.html` files.  To do this, we configured our development build to output to `target/public/demo-material-wave/out/main.js` and we configure our prod build to output to `out/main.js`.  This means that in all of our JS files we only have to specify `/demo-material-wave/out/main.js` and the path will work for local development and for github pages.

> The `demo-material-wave/public/` is a `:target-dir` needed by figwheel.  What we care about is everything after the `public/` segment.
