# eduCOVID


## Frontend development

1. `cd educovid-frontend` to enter frontend react app.
2. `npm install` to install project dependencies specified in its package.json.
3. `npm start` to start the app.

> You can use yarn, which is faster than npm. Install [here](https://classic.yarnpkg.com/en/docs/install/#debian-stable).

### Bootstrap

Bootstrap is installed in the project, so do not install it any other way.
Please do not try to use react other way that the refered in the documentation.
You may access the documentation [here](https://react-bootstrap.github.io/components/).

## Git

1. Never develop changes in master. Do `git checkout -b branchname` to create
a new branch. `-b` is what creates a new branch, so without it will change to
an existing branch if it exists. If you forgot to do this before development,
don't worry and do it once you notice it. Your changes will be in the new
branch.
2. Use `git add .` to add the files to the tracking system. You can specify
individual files instead by doing, for example `git add filename.txt
otherfile.js` and so. `git status` is a good command to check what you have
added so far.
3. Use `git commit -m "flag: small description"` where flag can be any value in
`feat`, `enh`, `fix`, `upd`, `doc` and the small description must summarize the
commit.
4. Use `git push` to send this changes to the remote (Github in our case) so
they are not only in your computer.
5. Do not merge to master without approval. After the push to remote, visit the
repository. You will be prompted to do a PR (Pull Request) to master. This means
asking your teams to review your changes so `master` can pull the changes you
made in your branch.

If you have any questions or think you messed up, ask for help.

Happy development!
