
# Speech Cannon

This is a small program I wrote to try and get create leverage for good ideas and outnumbered people. Despite the aggressive name, I'm actually hoping to improve effective communication under the pressures of the internet. 

The example data set provided here can be found running at http://www.trans-info.us ,but you can provide whatever topic is important to you, peak oil, nuclear energy, The Reformation... whatever you think isn't getting the detailed attention it deserves.

Speech Cannon is a simple web interface that allows topic providers to offer text clips in 3 sizes, along with a reference link, grouped by topic for easy lookup. Click the text or link buttons to move things to the top, selected, and ready to be copied to the buffer.

This is built using the Hoplon stack, which is built on clojure/script. For convenience, I use Heroku for too many things, including this project, starting here: https://github.com/tailrecursion/hoplon-deploy-heroku

For those of you looking to make use of this without experience in this stack, it will take a little work, but it is possible. You need Hoplon/Boot. Start at http://hoplon.io/ or https://github.com/hoplon/hoplon/wiki

In the end, you will need to clone this repo and replace everything between the []'s at: (def db [...]), then follow the instructions below to load everything up to your Heroku account. These instructions are left from the original hoplon-deploy-heroku read.me. Thanks Micha, for this starting point (and also to Alan, Micha's partner, for Hoplon). (If I'm doing open source wrong, I'm sure someone will let me know)

Each db entry is made up of 4 parts, and looks like:
{:chapter [] :links [""] :texts ["" "" ""] :tags #{"" }}

Fill in 2 numbers for chapter/section number. Then add whatever text you want between the quotes. You can add more quotes in the tags section, but tag filtering is not working at present. Just look at the file here, src/index.cljs.hl, for the pattern. The example db is used to run trans-info.us.

Finally, you will need to look in the body to find the faq text and replace that. 

Try to get a running start, especially in filling out your topic, and I'll be here if you get stuck. Everything below this point is Micha's original readme for getting a Hoplon app running on Heroku. (After you've loaded Boot, opened a Heroku account, cloned this project, and replaced db entries)


# Deploy Hoplon Application to Heroku

An example project that uses Hoplon and Castra, with tasks to help you package
it up and deploy to Heroku. If you aren't familiar with how Heroku works you
may want to [check out their Getting Started docs][1].

## Start Local Dev Server

- When you're working on your app you can start a local server that will serve
  your app at http://localhost:8000 and recompile source files as necessary:

  ```
  $ boot development
  ```

- When you have everything working the way you want it, you're ready to
  compile with optimizations and deploy to Heroku. That's the next section.

## Deploy to Heroku

- Set up for heroku (only need to do this once):

  ```
  $ heroku create
  ```

- Build the hoplon html etc. and prepare deployment descriptors for Heroku:

  ```
  $ boot production heroku
  ```

- Commit changes and push to Heroku:

  ```
  $ git commit -a -m "working"
  $ git push heroku master
  ```

- Check the server logs in case of trouble:

  ```
  $ heroku logs --tail
  ```

[1]: https://devcenter.heroku.com/articles/getting-started-with-clojure

## License

Copyright 2013 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
